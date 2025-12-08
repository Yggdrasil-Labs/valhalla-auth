package com.yggdrasil.labs.client.dto.co;

import java.time.LocalDateTime;

import com.alibaba.cola.dto.DTO;
import com.yggdrasil.labs.client.dto.enums.UserStatusEnum;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户认证信息对象
 *
 * <p>不包含敏感信息（如密码哈希）
 *
 * @author YoungerYang-Y
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AuthUserCO extends DTO {

    /** 用户ID */
    private Long userId;

    /** 状态 */
    private UserStatusEnum status;

    /** 最后登录时间 */
    private LocalDateTime lastLoginTime;

    /** 最后登录IP */
    private String lastLoginIp;

    /** 锁定截止时间 */
    private LocalDateTime lockedUntil;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;
}
