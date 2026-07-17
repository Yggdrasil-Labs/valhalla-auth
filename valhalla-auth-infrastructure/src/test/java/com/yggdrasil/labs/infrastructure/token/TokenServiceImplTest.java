package com.yggdrasil.labs.infrastructure.token;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;

import com.yggdrasil.labs.app.auth.service.RefreshTokenResult;
import com.yggdrasil.labs.app.auth.service.TokenPairResult;
import com.yggdrasil.labs.app.auth.service.TokenServiceException;
import com.yggdrasil.labs.app.auth.service.VerifyTokenResult;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;

/** TokenServiceImpl 单元测试 */
@ExtendWith(MockitoExtension.class)
class TokenServiceImplTest {

    @Mock private StringRedisTemplate redisTemplate;
    @Mock private ValueOperations<String, String> valueOps;
    @Mock private ZSetOperations<String, String> zSetOps;
    @Mock private JwtTokenProvider jwtTokenProvider;

    private TokenProperties tokenProperties;
    private TokenServiceImpl tokenService;

    @BeforeEach
    void setUp() {
        tokenProperties = new TokenProperties();
        tokenProperties.setAccessTokenTtl(Duration.ofMinutes(15));
        tokenProperties.setRefreshTokenTtl(Duration.ofDays(7));
        tokenProperties.setMaxSessions(5);
        tokenService = new TokenServiceImpl(jwtTokenProvider, redisTemplate, tokenProperties);
    }

    // ========== issueTokenPair ==========

    @Test
    void issueTokenPair_shouldStoreKeysInRedis() {
        when(jwtTokenProvider.generateToken(eq(1L), eq("access"), any())).thenReturn("at-jwt");
        when(jwtTokenProvider.generateToken(eq(1L), eq("refresh"), any())).thenReturn("rt-jwt");
        when(jwtTokenProvider.getJtiFromToken("at-jwt", false)).thenReturn("at-jti");
        when(jwtTokenProvider.getJtiFromToken("rt-jwt", false)).thenReturn("rt-jti");
        when(redisTemplate.opsForValue()).thenReturn(valueOps);
        when(redisTemplate.opsForZSet()).thenReturn(zSetOps);
        when(zSetOps.zCard(anyString())).thenReturn(2L);

        TokenPairResult result = tokenService.issueTokenPair(1L);

        assertEquals("at-jwt", result.getAccessToken());
        assertEquals("rt-jwt", result.getRefreshToken());
        assertEquals(900L, result.getExpiresIn());
        verify(valueOps).set(eq("token:access:at-jti"), eq("1:rt-jti"), eq(Duration.ofMinutes(15)));
        verify(valueOps).set(eq("token:refresh:rt-jti"), eq("1"), eq(Duration.ofDays(7)));
        verify(zSetOps).add(eq("user:tokens:1"), eq("at:at-jti"), anyDouble());
        verify(zSetOps).add(eq("user:tokens:1"), eq("rt:rt-jti"), anyDouble());
    }

    @Test
    void issueTokenPair_whenRedisUnavailable_shouldThrowException() {
        when(jwtTokenProvider.generateToken(eq(1L), eq("access"), any())).thenReturn("at-jwt");
        when(jwtTokenProvider.generateToken(eq(1L), eq("refresh"), any())).thenReturn("rt-jwt");
        when(jwtTokenProvider.getJtiFromToken("at-jwt", false)).thenReturn("at-jti");
        when(jwtTokenProvider.getJtiFromToken("rt-jwt", false)).thenReturn("rt-jti");
        when(redisTemplate.opsForValue()).thenReturn(valueOps);
        doThrow(new RedisConnectionFailureException("conn refused"))
                .when(valueOps)
                .set(anyString(), anyString(), any(Duration.class));

        TokenServiceException ex =
                assertThrows(TokenServiceException.class, () -> tokenService.issueTokenPair(1L));
        assertEquals("REDIS_UNAVAILABLE", ex.getErrorCode());
    }

    @Test
    void issueTokenPair_whenSessionsExceedMax_shouldEvictOldest() {
        when(jwtTokenProvider.generateToken(eq(1L), eq("access"), any())).thenReturn("at-jwt");
        when(jwtTokenProvider.generateToken(eq(1L), eq("refresh"), any())).thenReturn("rt-jwt");
        when(jwtTokenProvider.getJtiFromToken("at-jwt", false)).thenReturn("at-jti");
        when(jwtTokenProvider.getJtiFromToken("rt-jwt", false)).thenReturn("rt-jti");
        when(redisTemplate.opsForValue()).thenReturn(valueOps);
        when(redisTemplate.opsForZSet()).thenReturn(zSetOps);
        // 12 members = 6 sessions, exceeds max of 5 (10 members max)
        when(zSetOps.zCard("user:tokens:1")).thenReturn(12L).thenReturn(11L).thenReturn(10L);
        ZSetOperations.TypedTuple<String> tuple1 =
                ZSetOperations.TypedTuple.of("at:old-jti-1", 1000.0);
        ZSetOperations.TypedTuple<String> tuple2 =
                ZSetOperations.TypedTuple.of("rt:old-jti-2", 1001.0);
        when(zSetOps.popMin("user:tokens:1")).thenReturn(tuple1, tuple2);

        tokenService.issueTokenPair(1L);

        verify(zSetOps, times(2)).popMin("user:tokens:1");
        verify(redisTemplate).delete("token:access:old-jti-1");
        verify(redisTemplate).delete("token:refresh:old-jti-2");
    }

    // ========== verifyAccessToken ==========

    @Test
    void verifyAccessToken_withValidToken_shouldReturnResult() {
        Claims claims = buildClaims(1L, "jti1");
        when(jwtTokenProvider.parseToken("valid-token")).thenReturn(claims);
        when(redisTemplate.hasKey("token:access:jti1")).thenReturn(true);

        VerifyTokenResult result = tokenService.verifyAccessToken("valid-token");

        assertEquals(1L, result.getUserId());
        assertFalse(result.getDegraded());
    }

    @Test
    void verifyAccessToken_whenKeyNotExists_shouldThrowRevoked() {
        Claims claims = buildClaims(1L, "jti1");
        when(jwtTokenProvider.parseToken("valid-token")).thenReturn(claims);
        when(redisTemplate.hasKey("token:access:jti1")).thenReturn(false);

        TokenServiceException ex =
                assertThrows(
                        TokenServiceException.class,
                        () -> tokenService.verifyAccessToken("valid-token"));
        assertEquals("TOKEN_REVOKED", ex.getErrorCode());
    }

    @Test
    void verifyAccessToken_whenRedisUnavailable_shouldReturnDegraded() {
        Claims claims = buildClaims(1L, "jti1");
        when(jwtTokenProvider.parseToken("valid-token")).thenReturn(claims);
        when(redisTemplate.hasKey("token:access:jti1"))
                .thenThrow(new RedisConnectionFailureException(""));

        VerifyTokenResult result = tokenService.verifyAccessToken("valid-token");

        assertEquals(1L, result.getUserId());
        assertTrue(result.getDegraded());
    }

    @Test
    void verifyAccessToken_withExpiredToken_shouldThrowExpired() {
        when(jwtTokenProvider.parseToken("expired-token"))
                .thenThrow(new ExpiredJwtException(null, null, "expired"));

        TokenServiceException ex =
                assertThrows(
                        TokenServiceException.class,
                        () -> tokenService.verifyAccessToken("expired-token"));
        assertEquals("TOKEN_EXPIRED", ex.getErrorCode());
    }

    @Test
    void verifyAccessToken_withInvalidSignature_shouldThrowInvalid() {
        when(jwtTokenProvider.parseToken("tampered"))
                .thenThrow(new SignatureException("bad signature"));

        TokenServiceException ex =
                assertThrows(
                        TokenServiceException.class,
                        () -> tokenService.verifyAccessToken("tampered"));
        assertEquals("TOKEN_INVALID", ex.getErrorCode());
    }

    // ========== refreshAccessToken ==========

    @Test
    void refreshAccessToken_withValidRefreshToken_shouldReturnNewAT() {
        Claims claims = buildRefreshClaims(1L, "rt-jti");
        when(jwtTokenProvider.parseToken("valid-rt")).thenReturn(claims);
        when(redisTemplate.hasKey("token:refresh:rt-jti")).thenReturn(true);
        when(jwtTokenProvider.generateToken(eq(1L), eq("access"), any())).thenReturn("new-at");
        when(jwtTokenProvider.getJtiFromToken("new-at", false)).thenReturn("new-at-jti");
        when(redisTemplate.opsForValue()).thenReturn(valueOps);
        when(redisTemplate.opsForZSet()).thenReturn(zSetOps);

        RefreshTokenResult result = tokenService.refreshAccessToken("valid-rt");

        assertEquals("new-at", result.getAccessToken());
        assertEquals(900L, result.getExpiresIn());
        verify(valueOps)
                .set(eq("token:access:new-at-jti"), eq("1:rt-jti"), eq(Duration.ofMinutes(15)));
        verify(zSetOps).add(eq("user:tokens:1"), eq("at:new-at-jti"), anyDouble());
    }

    @Test
    void refreshAccessToken_whenRtRevoked_shouldThrowRevoked() {
        Claims claims = buildRefreshClaims(1L, "rt-jti");
        when(jwtTokenProvider.parseToken("revoked-rt")).thenReturn(claims);
        when(redisTemplate.hasKey("token:refresh:rt-jti")).thenReturn(false);

        TokenServiceException ex =
                assertThrows(
                        TokenServiceException.class,
                        () -> tokenService.refreshAccessToken("revoked-rt"));
        assertEquals("TOKEN_REVOKED", ex.getErrorCode());
    }

    @Test
    void refreshAccessToken_whenRedisUnavailable_shouldThrowUnavailable() {
        Claims claims = buildRefreshClaims(1L, "rt-jti");
        when(jwtTokenProvider.parseToken("rt")).thenReturn(claims);
        when(redisTemplate.hasKey("token:refresh:rt-jti"))
                .thenThrow(new RedisConnectionFailureException(""));

        TokenServiceException ex =
                assertThrows(
                        TokenServiceException.class, () -> tokenService.refreshAccessToken("rt"));
        assertEquals("REDIS_UNAVAILABLE", ex.getErrorCode());
    }

    @Test
    void refreshAccessToken_withAccessToken_shouldThrowInvalid() {
        Claims claims = buildClaims(1L, "at-jti"); // type="access"
        when(jwtTokenProvider.parseToken("access-token")).thenReturn(claims);

        TokenServiceException ex =
                assertThrows(
                        TokenServiceException.class,
                        () -> tokenService.refreshAccessToken("access-token"));
        assertEquals("TOKEN_INVALID", ex.getErrorCode());
    }

    // ========== revokeToken ==========

    @Test
    void revokeToken_shouldDeleteATAndRT() {
        when(jwtTokenProvider.getJtiFromToken("at-jwt", true)).thenReturn("at-jti");
        when(jwtTokenProvider.getUserIdFromToken("at-jwt", true)).thenReturn(1L);
        when(redisTemplate.opsForValue()).thenReturn(valueOps);
        when(valueOps.get("token:access:at-jti")).thenReturn("1:rt-jti");
        when(redisTemplate.opsForZSet()).thenReturn(zSetOps);

        tokenService.revokeToken("at-jwt");

        verify(redisTemplate).delete("token:access:at-jti");
        verify(redisTemplate).delete("token:refresh:rt-jti");
        verify(zSetOps).remove("user:tokens:1", "at:at-jti", "rt:rt-jti");
    }

    @Test
    void revokeToken_whenAtKeyExpired_shouldStillCleanZset() {
        when(jwtTokenProvider.getJtiFromToken("at-jwt", true)).thenReturn("at-jti");
        when(jwtTokenProvider.getUserIdFromToken("at-jwt", true)).thenReturn(1L);
        when(redisTemplate.opsForValue()).thenReturn(valueOps);
        when(valueOps.get("token:access:at-jti")).thenReturn(null);
        when(redisTemplate.opsForZSet()).thenReturn(zSetOps);

        tokenService.revokeToken("at-jwt");

        verify(redisTemplate).delete("token:access:at-jti");
        verify(zSetOps).remove("user:tokens:1", "at:at-jti");
    }

    @Test
    void revokeToken_whenRedisUnavailable_shouldThrow() {
        when(jwtTokenProvider.getJtiFromToken("at-jwt", true)).thenReturn("at-jti");
        when(jwtTokenProvider.getUserIdFromToken("at-jwt", true)).thenReturn(1L);
        when(redisTemplate.opsForValue()).thenReturn(valueOps);
        when(valueOps.get(anyString())).thenThrow(new RedisConnectionFailureException(""));

        TokenServiceException ex =
                assertThrows(TokenServiceException.class, () -> tokenService.revokeToken("at-jwt"));
        assertEquals("REDIS_UNAVAILABLE", ex.getErrorCode());
    }

    // ========== revokeAllTokens ==========

    @Test
    void revokeAllTokens_shouldDeleteAllKeysAndZset() {
        Set<String> members = new LinkedHashSet<>();
        members.add("at:jti1");
        members.add("rt:jti2");
        members.add("at:jti3");
        members.add("rt:jti4");
        when(redisTemplate.opsForZSet()).thenReturn(zSetOps);
        when(zSetOps.range("user:tokens:1", 0, -1)).thenReturn(members);

        tokenService.revokeAllTokens(1L);

        verify(redisTemplate).delete("token:access:jti1");
        verify(redisTemplate).delete("token:refresh:jti2");
        verify(redisTemplate).delete("token:access:jti3");
        verify(redisTemplate).delete("token:refresh:jti4");
        verify(redisTemplate).delete("user:tokens:1");
    }

    @Test
    void revokeAllTokens_whenRedisUnavailable_shouldThrow() {
        when(redisTemplate.opsForZSet()).thenReturn(zSetOps);
        when(zSetOps.range(anyString(), anyLong(), anyLong()))
                .thenThrow(new RedisConnectionFailureException(""));

        TokenServiceException ex =
                assertThrows(TokenServiceException.class, () -> tokenService.revokeAllTokens(1L));
        assertEquals("REDIS_UNAVAILABLE", ex.getErrorCode());
    }

    // ========== Helper methods ==========

    private Claims buildClaims(Long userId, String jti) {
        Claims claims = mock(Claims.class, withSettings().lenient());
        when(claims.getSubject()).thenReturn(userId.toString());
        when(claims.getId()).thenReturn(jti);
        when(claims.get("type", String.class)).thenReturn("access");
        when(claims.getExpiration()).thenReturn(Date.from(Instant.now().plusSeconds(900)));
        return claims;
    }

    private Claims buildRefreshClaims(Long userId, String jti) {
        Claims claims = mock(Claims.class, withSettings().lenient());
        when(claims.getSubject()).thenReturn(userId.toString());
        when(claims.getId()).thenReturn(jti);
        when(claims.get("type", String.class)).thenReturn("refresh");
        when(claims.getExpiration()).thenReturn(Date.from(Instant.now().plusSeconds(604800)));
        return claims;
    }
}
