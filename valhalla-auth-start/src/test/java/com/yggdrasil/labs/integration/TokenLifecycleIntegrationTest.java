package com.yggdrasil.labs.integration;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * Token 生命周期集成测试
 *
 * <p>需要 Docker 环境运行 Testcontainers
 */
@Disabled("需要 Docker 环境运行 Testcontainers")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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

    private static String accessToken;
    private static String refreshToken;

    @Test
    @Order(1)
    void login_shouldReturnTokens() {
        // TODO: 需要初始化测试用户数据
        Map<String, Object> body =
                Map.of(
                        "credentialType", "USERNAME",
                        "credentialValue", "admin",
                        "password", "correct123");
        ResponseEntity<Map> resp =
                restTemplate.postForEntity("/api/v1/auth/login", body, Map.class);
        assertEquals(200, resp.getStatusCode().value());
    }

    @Test
    @Order(2)
    void verify_shouldReturnUserId() {
        // 使用上一步获取的 accessToken
        Map<String, Object> body = Map.of("token", "test-access-token");
        ResponseEntity<Map> resp =
                restTemplate.postForEntity("/api/v1/auth/verify", body, Map.class);
        assertNotNull(resp.getBody());
    }

    @Test
    @Order(3)
    void refresh_shouldReturnNewAccessToken() {
        Map<String, Object> body = Map.of("refreshToken", "test-refresh-token");
        ResponseEntity<Map> resp =
                restTemplate.postForEntity("/api/v1/auth/refresh", body, Map.class);
        assertNotNull(resp.getBody());
    }

    @Test
    @Order(4)
    void logout_shouldSucceed() {
        Map<String, Object> body = Map.of("accessToken", "test-access-token", "userId", 1);
        ResponseEntity<Map> resp =
                restTemplate.postForEntity("/api/v1/auth/logout", body, Map.class);
        assertNotNull(resp.getBody());
    }

    @Test
    @Order(5)
    void verify_afterLogout_shouldFail() {
        Map<String, Object> body = Map.of("token", "test-access-token");
        ResponseEntity<Map> resp =
                restTemplate.postForEntity("/api/v1/auth/verify", body, Map.class);
        assertNotNull(resp.getBody());
    }
}
