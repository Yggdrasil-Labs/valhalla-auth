package com.yggdrasil.labs.app.auth.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** 令牌刷新结果 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenResult {

    /** 新的访问令牌 */
    private String accessToken;

    /** 过期时间（秒） */
    private Long expiresIn;
}
