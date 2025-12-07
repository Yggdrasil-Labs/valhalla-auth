package com.yggdrasil.labs.domain.auth.model;

import java.time.LocalDateTime;

import com.alibaba.cola.domain.Entity;
import com.yggdrasil.labs.domain.auth.model.enums.TokenType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Token实体
 *
 * <p>存储 Token 信息（用于审计和追踪，主要存储于 Redis）
 *
 * @author YoungerYang-Y
 */
@Data
@Entity
public class AuthToken {

    /** Token记录ID（主键） */
    private Long tokenId;

    /** 用户ID（关联用户服务，全局唯一） */
    private Long userId;

    /** Token类型 */
    private TokenType tokenType;

    /** Token哈希值（用于查询，不存储完整Token） */
    private String tokenHash;

    /** JWT ID（JTI） */
    private String jwtId;

    /** 设备ID */
    private String deviceId;

    /** 设备类型：WEB, MOBILE, API */
    private String deviceType;

    /** 设备名称 */
    private String deviceName;

    /** IP地址 */
    private String ipAddress;

    /** 用户代理 */
    private String userAgent;

    /** 登录地点（根据IP解析） */
    private String location;

    /** 签发时间 */
    private LocalDateTime issuedAt;

    /** 过期时间 */
    private LocalDateTime expiresAt;

    /** 最后使用时间 */
    private LocalDateTime lastUsedAt;

    /** 是否已撤销 */
    private Boolean revoked;

    /** 撤销时间 */
    private LocalDateTime revokedAt;

    /** 撤销原因 */
    private String revokeReason;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;

    /** Token创建参数 */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateParams {
        private Long userId;
        private TokenType tokenType;
        private String tokenHash;
        private String jwtId;
        private String deviceId;
        private String deviceType;
        private String deviceName;
        private String ipAddress;
        private String userAgent;
        private String location;
        private LocalDateTime expiresAt;
    }

    /** 创建Token记录 */
    public static AuthToken create(CreateParams params) {
        AuthToken token = new AuthToken();
        token.setUserId(params.getUserId());
        token.setTokenType(params.getTokenType());
        token.setTokenHash(params.getTokenHash());
        token.setJwtId(params.getJwtId());
        token.setDeviceId(params.getDeviceId());
        token.setDeviceType(params.getDeviceType());
        token.setDeviceName(params.getDeviceName());
        token.setIpAddress(params.getIpAddress());
        token.setUserAgent(params.getUserAgent());
        token.setLocation(params.getLocation());
        token.setIssuedAt(LocalDateTime.now());
        token.setExpiresAt(params.getExpiresAt());
        token.setRevoked(false);
        token.setCreateTime(LocalDateTime.now());
        token.setUpdateTime(LocalDateTime.now());
        return token;
    }

    /** 记录使用时间 */
    public void recordUsage() {
        this.lastUsedAt = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
    }

    /** 撤销Token */
    public void revoke(String reason) {
        this.revoked = true;
        this.revokedAt = LocalDateTime.now();
        this.revokeReason = reason;
        this.updateTime = LocalDateTime.now();
    }

    /** 检查Token是否已过期 */
    public boolean isExpired() {
        return expiresAt != null && LocalDateTime.now().isAfter(expiresAt);
    }

    /** 检查Token是否有效 */
    public boolean isValid() {
        return !revoked && !isExpired();
    }
}
