package com.yggdrasil.labs.infrastructure.token;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Set;

import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import com.yggdrasil.labs.app.auth.service.RefreshTokenResult;
import com.yggdrasil.labs.app.auth.service.TokenPairResult;
import com.yggdrasil.labs.app.auth.service.TokenService;
import com.yggdrasil.labs.app.auth.service.TokenServiceException;
import com.yggdrasil.labs.app.auth.service.VerifyTokenResult;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Token 服务实现
 *
 * <p>使用 JwtTokenProvider 签发/解析 JWT，通过 StringRedisTemplate 管理 Token 元数据（白名单模式）
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private static final String ACCESS_KEY_PREFIX = "token:access:";
    private static final String REFRESH_KEY_PREFIX = "token:refresh:";
    private static final String USER_TOKENS_PREFIX = "user:tokens:";

    private final JwtTokenProvider jwtTokenProvider;
    private final StringRedisTemplate redisTemplate;
    private final TokenProperties tokenProperties;

    @Override
    public TokenPairResult issueTokenPair(Long userId) {
        try {
            Duration accessTtl = tokenProperties.getAccessTokenTtl();
            Duration refreshTtl = tokenProperties.getRefreshTokenTtl();

            // 签发 AT 和 RT
            String accessToken = jwtTokenProvider.generateToken(userId, "access", accessTtl);
            String refreshToken = jwtTokenProvider.generateToken(userId, "refresh", refreshTtl);

            String atJti = jwtTokenProvider.getJtiFromToken(accessToken, false);
            String rtJti = jwtTokenProvider.getJtiFromToken(refreshToken, false);

            // 存储 AT key: value = "{userId}:{rtJti}"
            redisTemplate
                    .opsForValue()
                    .set(ACCESS_KEY_PREFIX + atJti, userId + ":" + rtJti, accessTtl);

            // 存储 RT key: value = userId
            redisTemplate
                    .opsForValue()
                    .set(REFRESH_KEY_PREFIX + rtJti, userId.toString(), refreshTtl);

            // ZSET 记录会话
            String userTokensKey = USER_TOKENS_PREFIX + userId;
            double score = (double) System.currentTimeMillis();
            ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();
            zSetOps.add(userTokensKey, "at:" + atJti, score);
            zSetOps.add(userTokensKey, "rt:" + rtJti, score);

            // 检查会话数限制，FIFO 踢出超限会话
            evictExcessSessions(userId, userTokensKey);

            return new TokenPairResult(accessToken, refreshToken, accessTtl.toSeconds());
        } catch (RedisConnectionFailureException e) {
            log.error("Redis unavailable during token issuance for userId={}", userId, e);
            throw TokenServiceException.redisUnavailable();
        }
    }

    @Override
    public VerifyTokenResult verifyAccessToken(String accessToken) {
        Claims claims;
        try {
            claims = jwtTokenProvider.parseToken(accessToken);
        } catch (ExpiredJwtException e) {
            throw TokenServiceException.tokenExpired();
        } catch (SignatureException | IllegalArgumentException e) {
            throw TokenServiceException.tokenInvalid();
        }

        String jti = claims.getId();
        Long userId = Long.parseLong(claims.getSubject());
        LocalDateTime expiresAt =
                LocalDateTime.ofInstant(claims.getExpiration().toInstant(), ZoneId.systemDefault());

        // Redis 存在性检查
        try {
            Boolean exists = redisTemplate.hasKey(ACCESS_KEY_PREFIX + jti);
            if (Boolean.FALSE.equals(exists)) {
                throw TokenServiceException.tokenRevoked();
            }
            return new VerifyTokenResult(userId, expiresAt, false);
        } catch (RedisConnectionFailureException e) {
            // Redis 不可用 → 降级放行
            log.warn(
                    "Redis unavailable during token verification, degrading for userId={}", userId);
            return new VerifyTokenResult(userId, expiresAt, true);
        }
    }

    @Override
    public RefreshTokenResult refreshAccessToken(String refreshToken) {
        Claims claims;
        try {
            claims = jwtTokenProvider.parseToken(refreshToken);
        } catch (ExpiredJwtException e) {
            throw TokenServiceException.tokenExpired();
        } catch (SignatureException | IllegalArgumentException e) {
            throw TokenServiceException.tokenInvalid();
        }

        // 校验 type 必须是 refresh
        String type = claims.get("type", String.class);
        if (!"refresh".equals(type)) {
            throw TokenServiceException.tokenInvalid();
        }

        String rtJti = claims.getId();
        Long userId = Long.parseLong(claims.getSubject());

        try {
            // 检查 RT 是否存在（未被吊销）
            Boolean exists = redisTemplate.hasKey(REFRESH_KEY_PREFIX + rtJti);
            if (Boolean.FALSE.equals(exists)) {
                throw TokenServiceException.tokenRevoked();
            }

            // 签发新 AT
            Duration accessTtl = tokenProperties.getAccessTokenTtl();
            String newAccessToken = jwtTokenProvider.generateToken(userId, "access", accessTtl);
            String newAtJti = jwtTokenProvider.getJtiFromToken(newAccessToken, false);

            // 存储新 AT
            redisTemplate
                    .opsForValue()
                    .set(ACCESS_KEY_PREFIX + newAtJti, userId + ":" + rtJti, accessTtl);

            // 添加到 ZSET
            String userTokensKey = USER_TOKENS_PREFIX + userId;
            double score = (double) System.currentTimeMillis();
            redisTemplate.opsForZSet().add(userTokensKey, "at:" + newAtJti, score);

            return new RefreshTokenResult(newAccessToken, accessTtl.toSeconds());
        } catch (RedisConnectionFailureException e) {
            log.error("Redis unavailable during token refresh for userId={}", userId, e);
            throw TokenServiceException.redisUnavailable();
        }
    }

    @Override
    public void revokeToken(String accessToken) {
        try {
            String atJti = jwtTokenProvider.getJtiFromToken(accessToken, true);
            Long userId = jwtTokenProvider.getUserIdFromToken(accessToken, true);

            // 获取 AT value 以提取关联的 rtJti
            String atValue = redisTemplate.opsForValue().get(ACCESS_KEY_PREFIX + atJti);

            // 删除 AT key
            redisTemplate.delete(ACCESS_KEY_PREFIX + atJti);

            // 如果能获取到关联的 rtJti，也删除 RT
            if (atValue != null && atValue.contains(":")) {
                String rtJti = atValue.substring(atValue.indexOf(':') + 1);
                redisTemplate.delete(REFRESH_KEY_PREFIX + rtJti);

                // 从 ZSET 移除
                String userTokensKey = USER_TOKENS_PREFIX + userId;
                redisTemplate.opsForZSet().remove(userTokensKey, "at:" + atJti, "rt:" + rtJti);
            } else {
                // AT key 已过期，仅清理 ZSET
                String userTokensKey = USER_TOKENS_PREFIX + userId;
                redisTemplate.opsForZSet().remove(userTokensKey, "at:" + atJti);
            }
        } catch (RedisConnectionFailureException e) {
            log.error("Redis unavailable during token revocation", e);
            throw TokenServiceException.redisUnavailable();
        }
    }

    @Override
    public void revokeAllTokens(Long userId) {
        try {
            String userTokensKey = USER_TOKENS_PREFIX + userId;

            // 获取所有成员
            Set<String> members = redisTemplate.opsForZSet().range(userTokensKey, 0, -1);
            if (members != null && !members.isEmpty()) {
                for (String member : members) {
                    if (member.startsWith("at:")) {
                        String jti = member.substring(3);
                        redisTemplate.delete(ACCESS_KEY_PREFIX + jti);
                    } else if (member.startsWith("rt:")) {
                        String jti = member.substring(3);
                        redisTemplate.delete(REFRESH_KEY_PREFIX + jti);
                    }
                }
            }

            // 删除 ZSET 本身
            redisTemplate.delete(userTokensKey);
        } catch (RedisConnectionFailureException e) {
            log.error("Redis unavailable during revokeAll for userId={}", userId, e);
            throw TokenServiceException.redisUnavailable();
        }
    }

    /** 会话数超限时踢出最早的会话（FIFO） */
    private void evictExcessSessions(Long userId, String userTokensKey) {
        Long memberCount = redisTemplate.opsForZSet().zCard(userTokensKey);
        if (memberCount == null) {
            return;
        }

        // 每个会话占 2 个成员（at + rt）
        int maxMembers = tokenProperties.getMaxSessions() * 2;
        while (memberCount > maxMembers) {
            // ZPOPMIN 弹出最早的成员
            ZSetOperations.TypedTuple<String> oldest =
                    redisTemplate.opsForZSet().popMin(userTokensKey);
            if (oldest == null || oldest.getValue() == null) {
                break;
            }

            String member = oldest.getValue();
            // 删除对应的 token key
            if (member.startsWith("at:")) {
                redisTemplate.delete(ACCESS_KEY_PREFIX + member.substring(3));
            } else if (member.startsWith("rt:")) {
                redisTemplate.delete(REFRESH_KEY_PREFIX + member.substring(3));
            }
            memberCount--;
        }
    }
}
