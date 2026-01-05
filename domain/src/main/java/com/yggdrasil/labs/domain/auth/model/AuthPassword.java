package com.yggdrasil.labs.domain.auth.model;

import java.time.LocalDateTime;

import com.alibaba.cola.domain.Entity;
import com.yggdrasil.labs.domain.auth.model.enums.PasswordAlgo;
import com.yggdrasil.labs.domain.auth.model.enums.PasswordStatus;

import lombok.Data;

/**
 * 用户密码实体
 *
 * <p>存储用户密码相关信息，支持多种密码算法和密码状态管理
 *
 * @author YoungerYang-Y
 */
@Data
@Entity
public class AuthPassword {

    /** 用户ID（主键，关联用户服务，全局唯一） */
    private Long userId;

    /** 密码哈希值（BCrypt等） */
    private String passwordHash;

    /** 密码算法 */
    private PasswordAlgo passwordAlgo;

    /** 密码策略版本（用于密码升级） */
    private Integer passwordVersion;

    /** 密码状态 */
    private PasswordStatus passwordStatus;

    /** 是否强制改密：false-否, true-是 */
    private Boolean forceChange;

    /** 密码过期时间 */
    private LocalDateTime passwordExpiresAt;

    /** 最后修改时间 */
    private LocalDateTime changedAt;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;

    /** 创建密码 */
    public static AuthPassword create(Long userId, String passwordHash) {
        AuthPassword password = new AuthPassword();
        password.setUserId(userId);
        password.setPasswordHash(passwordHash);
        password.setPasswordAlgo(PasswordAlgo.BCRYPT);
        password.setPasswordVersion(1);
        password.setPasswordStatus(PasswordStatus.VALID);
        password.setForceChange(false);
        password.setChangedAt(LocalDateTime.now());
        password.setCreateTime(LocalDateTime.now());
        password.setUpdateTime(LocalDateTime.now());
        return password;
    }

    /** 修改密码 */
    public void changePassword(String newPasswordHash) {
        this.passwordHash = newPasswordHash;
        this.changedAt = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
        // 修改密码后重置强制改密标志
        this.forceChange = false;
    }

    /** 检查密码是否过期 */
    public boolean isExpired() {
        if (passwordExpiresAt == null) {
            return false;
        }
        return LocalDateTime.now().isAfter(passwordExpiresAt);
    }

    /** 检查是否需要强制改密 */
    public boolean isForceChangeRequired() {
        return Boolean.TRUE.equals(forceChange);
    }

    /** 检查密码是否有效 */
    public boolean isValid() {
        return passwordStatus == PasswordStatus.VALID && !isExpired();
    }

    /** 设置强制改密 */
    public void setForceChange(boolean forceChange) {
        this.forceChange = forceChange;
        this.updateTime = LocalDateTime.now();
    }

    /** 设置密码过期时间 */
    public void setPasswordExpiresAt(LocalDateTime passwordExpiresAt) {
        this.passwordExpiresAt = passwordExpiresAt;
        this.updateTime = LocalDateTime.now();
    }
}
