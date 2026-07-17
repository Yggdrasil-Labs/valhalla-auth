package com.yggdrasil.labs.infrastructure.token;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Token 模块配置
 *
 * <p>启用 JWT 和 Token 配置属性绑定
 */
@Configuration
@EnableConfigurationProperties({JwtProperties.class, TokenProperties.class})
public class TokenConfig {}
