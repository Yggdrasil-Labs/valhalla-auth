package com.yggdrasil.labs.app.auth.dto.co;

import java.time.LocalDateTime;

import com.alibaba.cola.dto.DTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Token 验证结果对象
 *
 * <p>返回给 adapter 层的验证结果
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class VerifyTokenCO extends DTO {

    /** 用户ID */
    private Long userId;

    /** 过期时间 */
    private LocalDateTime expiresAt;

    /** 是否降级模式 */
    private Boolean degraded;
}
