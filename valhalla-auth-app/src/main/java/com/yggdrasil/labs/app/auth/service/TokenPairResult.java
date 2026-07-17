package com.yggdrasil.labs.app.auth.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** 令牌对签发结果 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenPairResult {

    /** 访问令牌 */
    private String accessToken;

    /** 刷新令牌 */
    private String refreshToken;

    /** 过期时间（秒） */
    private Long expiresIn;
}
