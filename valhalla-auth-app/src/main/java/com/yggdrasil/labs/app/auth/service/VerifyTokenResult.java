package com.yggdrasil.labs.app.auth.service;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** 令牌验证结果 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerifyTokenResult {

    /** 用户ID */
    private Long userId;

    /** 过期时间 */
    private LocalDateTime expiresAt;

    /** 是否降级模式（Redis 不可用时为 true） */
    private Boolean degraded;
}
