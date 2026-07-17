package com.yggdrasil.labs.infrastructure.token;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import javax.crypto.SecretKey;

import jakarta.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

/**
 * JWT Token 提供者
 *
 * <p>封装 jjwt 库，提供 JWT 签发和解析能力
 */
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;
    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        String secret = jwtProperties.getSecret();
        if (secret == null || secret.getBytes().length < 32) {
            throw new IllegalArgumentException("JWT secret must be at least 256 bits (32 bytes)");
        }
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
    }

    /**
     * 生成 JWT Token
     *
     * @param userId 用户ID
     * @param type Token 类型 (access/refresh)
     * @param ttl 有效期
     * @return JWT 字符串
     */
    public String generateToken(Long userId, String type, Duration ttl) {
        Instant now = Instant.now();
        return Jwts.builder()
                .subject(userId.toString())
                .id(UUID.randomUUID().toString())
                .claim("type", type)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plus(ttl)))
                .signWith(secretKey)
                .compact();
    }

    /**
     * 解析并校验 JWT Token
     *
     * @param token JWT 字符串
     * @return Claims
     */
    public Claims parseToken(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
    }

    /**
     * 从 Token 中提取 JTI
     *
     * @param token JWT 字符串
     * @param allowExpired 是否允许已过期的 token
     * @return JTI
     */
    public String getJtiFromToken(String token, boolean allowExpired) {
        try {
            return parseToken(token).getId();
        } catch (ExpiredJwtException e) {
            if (allowExpired) {
                return e.getClaims().getId();
            }
            throw e;
        }
    }

    /**
     * 从 Token 中提取用户ID
     *
     * @param token JWT 字符串
     * @param allowExpired 是否允许已过期的 token
     * @return 用户ID
     */
    public Long getUserIdFromToken(String token, boolean allowExpired) {
        try {
            return Long.parseLong(parseToken(token).getSubject());
        } catch (ExpiredJwtException e) {
            if (allowExpired) {
                return Long.parseLong(e.getClaims().getSubject());
            }
            throw e;
        }
    }
}
