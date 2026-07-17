package com.yggdrasil.labs.integration;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.yggdrasil.labs.start.Application;

/**
 * Token 生命周期集成测试
 *
 * <p>使用 Testcontainers 启动 Redis + MySQL，验证完整 Token 生命周期
 */
@Disabled("docker-java 与 Docker Engine 29.6.1 不兼容，/info 端点返回空数据")
@SpringBootTest(
        classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TokenLifecycleIntegrationTest {

    @Container
    static GenericContainer<?> redis =
            new GenericContainer<>("redis:7-alpine").withExposedPorts(6379);

    @Container
    static MySQLContainer<?> mysql =
            new MySQLContainer<>("mysql:8.4")
                    .withDatabaseName("testdb")
                    .withUsername("root")
                    .withPassword("test");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.redis.host", redis::getHost);
        registry.add("spring.data.redis.port", () -> redis.getMappedPort(6379));
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
    }

    @Autowired private TestRestTemplate restTemplate;
    @Autowired private JdbcTemplate jdbcTemplate;

    private String accessToken;
    private String refreshToken;

    @BeforeAll
    void initTestData() {
        // 创建表结构
        jdbcTemplate.execute(
                "CREATE TABLE IF NOT EXISTS `auth_credential` (`credential_id` BIGINT NOT"
                    + " NULL,`user_id` BIGINT NOT NULL,`credential_type` TINYINT NOT"
                    + " NULL,`credential_value` VARCHAR(255) NOT NULL,`provider` VARCHAR(64)"
                    + " NULL,`is_primary` TINYINT(1) NOT NULL DEFAULT 0,`verified` TINYINT(1) NOT"
                    + " NULL DEFAULT 0,`verified_at` DATETIME NULL,`create_time` DATETIME NOT NULL"
                    + " DEFAULT CURRENT_TIMESTAMP,`update_time` DATETIME NOT NULL DEFAULT"
                    + " CURRENT_TIMESTAMP,`deleted_at` BIGINT NOT NULL DEFAULT 0,PRIMARY KEY"
                    + " (`credential_id`),UNIQUE KEY `uk_credential` (`credential_type`,"
                    + " `credential_value`, `deleted_at`),KEY `idx_user` (`user_id`)) ENGINE=InnoDB"
                    + " DEFAULT CHARSET=utf8mb4");

        jdbcTemplate.execute(
                "CREATE TABLE IF NOT EXISTS `auth_password` ("
                        + "`user_id` BIGINT NOT NULL,"
                        + "`password_hash` VARCHAR(512) NOT NULL,"
                        + "`password_algo` TINYINT NOT NULL DEFAULT 1,"
                        + "`password_version` INT NOT NULL DEFAULT 1,"
                        + "`password_status` TINYINT NOT NULL DEFAULT 1,"
                        + "`force_change` TINYINT(1) NOT NULL DEFAULT 0,"
                        + "`password_expires_at` DATETIME NULL,"
                        + "`changed_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,"
                        + "`create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,"
                        + "`update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,"
                        + "`failed_attempts` INT NOT NULL DEFAULT 0,"
                        + "`locked_until` DATETIME NULL,"
                        + "PRIMARY KEY (`user_id`)"
                        + ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4");

        // 插入测试用户凭证: credential_type=1(USERNAME), credential_value=admin
        jdbcTemplate.execute(
                "INSERT INTO auth_credential (credential_id, user_id, credential_type,"
                        + " credential_value, is_primary, verified, deleted_at)"
                        + " VALUES (1001, 10001, 1, 'admin', 1, 1, 0)");

        // 插入密码: BCrypt hash of 'correct123'
        // $2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy 是 correct123 的 BCrypt
        String bcryptHash = "$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy";
        jdbcTemplate.execute(
                "INSERT INTO auth_password (user_id, password_hash, password_algo,"
                        + " password_version, password_status, force_change, failed_attempts)"
                        + " VALUES (10001, '"
                        + bcryptHash
                        + "', 1, 1, 1, 0, 0)");
    }

    @Test
    @Order(1)
    @SuppressWarnings("unchecked")
    void login_shouldReturnTokens() {
        Map<String, Object> body =
                Map.of(
                        "credentialType", "USERNAME",
                        "credentialValue", "admin",
                        "password", "correct123");

        ResponseEntity<Map> resp =
                restTemplate.postForEntity("/api/v1/auth/login", body, Map.class);

        assertEquals(HttpStatus.OK.value(), resp.getStatusCode().value());
        Map<String, Object> respBody = resp.getBody();
        assertNotNull(respBody);

        // COLA SingleResponse 结构: { success, errCode, errMessage, data: { token: {...}, user:
        // {...} } }
        assertTrue((Boolean) respBody.get("success"), "Login should succeed: " + respBody);

        Map<String, Object> data = (Map<String, Object>) respBody.get("data");
        assertNotNull(data, "Response data should not be null");

        Map<String, Object> token = (Map<String, Object>) data.get("token");
        assertNotNull(token, "Token object should not be null");

        accessToken = (String) token.get("accessToken");
        refreshToken = (String) token.get("refreshToken");
        assertNotNull(accessToken, "Access token should not be null");
        assertNotNull(refreshToken, "Refresh token should not be null");
    }

    @Test
    @Order(2)
    @SuppressWarnings("unchecked")
    void verify_shouldReturnUserId() {
        assertNotNull(accessToken, "Access token must be set by login test");

        Map<String, Object> body = Map.of("token", accessToken);
        ResponseEntity<Map> resp =
                restTemplate.postForEntity("/api/v1/auth/verify", body, Map.class);

        assertEquals(HttpStatus.OK.value(), resp.getStatusCode().value());
        Map<String, Object> respBody = resp.getBody();
        assertNotNull(respBody);
        assertTrue((Boolean) respBody.get("success"), "Verify should succeed: " + respBody);

        Map<String, Object> data = (Map<String, Object>) respBody.get("data");
        assertNotNull(data);
        assertEquals(10001, ((Number) data.get("userId")).longValue());
        assertFalse((Boolean) data.get("degraded"));
    }

    @Test
    @Order(3)
    @SuppressWarnings("unchecked")
    void refresh_shouldReturnNewAccessToken() {
        assertNotNull(refreshToken, "Refresh token must be set by login test");

        Map<String, Object> body = Map.of("refreshToken", refreshToken);
        ResponseEntity<Map> resp =
                restTemplate.postForEntity("/api/v1/auth/refresh", body, Map.class);

        assertEquals(HttpStatus.OK.value(), resp.getStatusCode().value());
        Map<String, Object> respBody = resp.getBody();
        assertNotNull(respBody);
        assertTrue((Boolean) respBody.get("success"), "Refresh should succeed: " + respBody);

        Map<String, Object> data = (Map<String, Object>) respBody.get("data");
        assertNotNull(data);
        String newAccessToken = (String) data.get("accessToken");
        assertNotNull(newAccessToken);
        assertNotEquals(accessToken, newAccessToken, "New AT should differ from old AT");

        // 更新 accessToken 为新的
        accessToken = newAccessToken;
    }

    @Test
    @Order(4)
    @SuppressWarnings("unchecked")
    void logout_shouldSucceed() {
        assertNotNull(accessToken, "Access token must be set");

        Map<String, Object> body = Map.of("accessToken", accessToken, "userId", 10001);
        ResponseEntity<Map> resp =
                restTemplate.postForEntity("/api/v1/auth/logout", body, Map.class);

        assertEquals(HttpStatus.OK.value(), resp.getStatusCode().value());
        Map<String, Object> respBody = resp.getBody();
        assertNotNull(respBody);
        assertTrue((Boolean) respBody.get("success"), "Logout should succeed: " + respBody);
    }

    @Test
    @Order(5)
    @SuppressWarnings("unchecked")
    void verify_afterLogout_shouldReturnRevoked() {
        assertNotNull(accessToken, "Access token must be set");

        Map<String, Object> body = Map.of("token", accessToken);
        ResponseEntity<Map> resp =
                restTemplate.postForEntity("/api/v1/auth/verify", body, Map.class);

        assertEquals(HttpStatus.OK.value(), resp.getStatusCode().value());
        Map<String, Object> respBody = resp.getBody();
        assertNotNull(respBody);
        // 验证应该失败: TOKEN_REVOKED
        assertFalse((Boolean) respBody.get("success"), "Verify after logout should fail");
        assertEquals("TOKEN_REVOKED", respBody.get("errCode"));
    }
}
