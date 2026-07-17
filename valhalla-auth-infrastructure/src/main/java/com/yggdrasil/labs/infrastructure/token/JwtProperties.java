package com.yggdrasil.labs.infrastructure.token;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/** JWT 配置属性 */
@Data
@ConfigurationProperties(prefix = "auth.jwt")
public class JwtProperties {

    /** HS256 签名密钥，≥ 256 位 */
    private String secret;
}
