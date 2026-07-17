package com.yggdrasil.labs.infrastructure.token;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;

/** JwtTokenProvider 单元测试 */
class JwtTokenProviderTest {

    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setUp() {
        JwtProperties props = new JwtProperties();
        props.setSecret("this-is-a-test-secret-key-that-is-at-least-32-bytes-long-for-hs256");
        jwtTokenProvider = new JwtTokenProvider(props);
        jwtTokenProvider.init();
    }

    @Test
    void generateAndParse_shouldReturnCorrectClaims() {
        String token = jwtTokenProvider.generateToken(10001L, "access", Duration.ofMinutes(15));
        Claims claims = jwtTokenProvider.parseToken(token);

        assertEquals("10001", claims.getSubject());
        assertEquals("access", claims.get("type", String.class));
        assertNotNull(claims.getId());
        assertNotNull(claims.getIssuedAt());
        assertNotNull(claims.getExpiration());
    }

    @Test
    void parseToken_withTamperedSignature_shouldThrowException() {
        String token = jwtTokenProvider.generateToken(1L, "access", Duration.ofMinutes(15));
        String tampered = token.substring(0, token.lastIndexOf('.') + 1) + "invalid";

        assertThrows(Exception.class, () -> jwtTokenProvider.parseToken(tampered));
    }

    @Test
    void parseToken_withExpiredToken_shouldThrowExpiredException() throws InterruptedException {
        String token = jwtTokenProvider.generateToken(1L, "access", Duration.ofMillis(1));
        Thread.sleep(50);

        assertThrows(ExpiredJwtException.class, () -> jwtTokenProvider.parseToken(token));
    }

    @Test
    void getJtiFromToken_withExpiredToken_allowExpired_shouldReturnJti()
            throws InterruptedException {
        String token = jwtTokenProvider.generateToken(1L, "access", Duration.ofSeconds(1));
        String jti = jwtTokenProvider.getJtiFromToken(token, false);
        assertNotNull(jti);

        Thread.sleep(1500);

        String jtiFromExpired = jwtTokenProvider.getJtiFromToken(token, true);
        assertEquals(jti, jtiFromExpired);
    }

    @Test
    void getJtiFromToken_withExpiredToken_notAllowExpired_shouldThrow()
            throws InterruptedException {
        String token = jwtTokenProvider.generateToken(1L, "access", Duration.ofMillis(1));
        Thread.sleep(50);

        assertThrows(
                ExpiredJwtException.class, () -> jwtTokenProvider.getJtiFromToken(token, false));
    }

    @Test
    void getUserIdFromToken_shouldReturnUserId() {
        String token = jwtTokenProvider.generateToken(42L, "access", Duration.ofMinutes(15));

        Long userId = jwtTokenProvider.getUserIdFromToken(token, false);

        assertEquals(42L, userId);
    }

    @Test
    void getUserIdFromToken_withExpiredToken_allowExpired_shouldReturnUserId()
            throws InterruptedException {
        String token = jwtTokenProvider.generateToken(42L, "access", Duration.ofMillis(1));
        Thread.sleep(50);

        Long userId = jwtTokenProvider.getUserIdFromToken(token, true);

        assertEquals(42L, userId);
    }

    @Test
    void init_withShortSecret_shouldThrowException() {
        JwtProperties props = new JwtProperties();
        props.setSecret("short");
        JwtTokenProvider provider = new JwtTokenProvider(props);

        assertThrows(IllegalArgumentException.class, provider::init);
    }

    @Test
    void init_withNullSecret_shouldThrowException() {
        JwtProperties props = new JwtProperties();
        props.setSecret(null);
        JwtTokenProvider provider = new JwtTokenProvider(props);

        assertThrows(IllegalArgumentException.class, provider::init);
    }

    @Test
    void generateToken_shouldProduceUniqueJti() {
        String token1 = jwtTokenProvider.generateToken(1L, "access", Duration.ofMinutes(15));
        String token2 = jwtTokenProvider.generateToken(1L, "access", Duration.ofMinutes(15));

        String jti1 = jwtTokenProvider.getJtiFromToken(token1, false);
        String jti2 = jwtTokenProvider.getJtiFromToken(token2, false);

        assertNotEquals(jti1, jti2);
    }
}
