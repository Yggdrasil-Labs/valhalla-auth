package com.yggdrasil.labs.infrastructure.token;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/** Token 配置属性 */
@Data
@ConfigurationProperties(prefix = "auth.token")
public class TokenProperties {

    /** Access Token 有效期，默认 15 分钟 */
    private Duration accessTokenTtl = Duration.ofMinutes(15);

    /** Refresh Token 有效期，默认 7 天 */
    private Duration refreshTokenTtl = Duration.ofDays(7);

    /** 单用户最大并发会话数，默认 5 */
    private int maxSessions = 5;
}
