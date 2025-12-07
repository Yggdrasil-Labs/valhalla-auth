package com.yggdrasil.labs.domain.auth.model;

import java.time.LocalDateTime;

import com.alibaba.cola.domain.Entity;
import com.yggdrasil.labs.domain.auth.model.enums.UserStatus;

import lombok.Data;

/**
 * 用户认证聚合根
 *
 * <p>用户认证领域的聚合根，管理用户认证相关的所有信息
 *
 * @author YoungerYang-Y
 */
@Data
@Entity
public class AuthUser {

    /** 用户ID（主键，关联用户服务，全局唯一，雪花ID） */
    private Long userId;

    /** 密码哈希值（BCrypt，三方登录可为空） */
    private String passwordHash;

    /** 密码最后修改时间 */
    private LocalDateTime passwordChangedTime;

    /** 最后登录时间 */
    private LocalDateTime lastLoginTime;

    /** 最后登录IP */
    private String lastLoginIp;

    /** 状态 */
    private UserStatus status;

    /** 锁定截止时间 */
    private LocalDateTime lockedUntil;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;

    /** 创建用户认证信息 */
    public static AuthUser create(Long userId, String passwordHash) {
        AuthUser authUser = new AuthUser();
        authUser.setUserId(userId);
        authUser.setPasswordHash(passwordHash);
        authUser.setStatus(UserStatus.NORMAL);
        authUser.setPasswordChangedTime(LocalDateTime.now());
        authUser.setCreateTime(LocalDateTime.now());
        authUser.setUpdateTime(LocalDateTime.now());
        return authUser;
    }

    /** 更新密码 */
    public void changePassword(String newPasswordHash) {
        this.passwordHash = newPasswordHash;
        this.passwordChangedTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
    }

    /** 记录登录信息 */
    public void recordLogin(String loginIp) {
        this.lastLoginTime = LocalDateTime.now();
        this.lastLoginIp = loginIp;
        this.updateTime = LocalDateTime.now();
    }

    /** 锁定账户 */
    public void lock(LocalDateTime lockedUntil) {
        this.status = UserStatus.LOCKED;
        this.lockedUntil = lockedUntil;
        this.updateTime = LocalDateTime.now();
    }

    /** 解锁账户 */
    public void unlock() {
        if (this.status == UserStatus.LOCKED) {
            this.status = UserStatus.NORMAL;
            this.lockedUntil = null;
            this.updateTime = LocalDateTime.now();
        }
    }

    /** 禁用账户 */
    public void disable() {
        this.status = UserStatus.DISABLED;
        this.updateTime = LocalDateTime.now();
    }

    /** 启用账户 */
    public void enable() {
        if (this.status == UserStatus.DISABLED) {
            this.status = UserStatus.NORMAL;
            this.updateTime = LocalDateTime.now();
        }
    }

    /** 检查账户是否可用 */
    public boolean isAvailable() {
        if (status == null || !status.isAvailable()) {
            return false;
        }
        // 检查是否在锁定期间
        if (lockedUntil != null && LocalDateTime.now().isBefore(lockedUntil)) {
            return false;
        }
        return true;
    }

    /** 检查账户是否被锁定 */
    public boolean isLocked() {
        return status != null
                && status.isLocked()
                && lockedUntil != null
                && LocalDateTime.now().isBefore(lockedUntil);
    }
}
