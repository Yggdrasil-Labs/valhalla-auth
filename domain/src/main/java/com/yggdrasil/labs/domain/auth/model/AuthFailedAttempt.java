package com.yggdrasil.labs.domain.auth.model;

import java.time.LocalDateTime;

import com.alibaba.cola.domain.Entity;
import com.yggdrasil.labs.domain.auth.model.enums.CredentialType;

import lombok.Data;

/**
 * 失败尝试实体（防爆破）
 *
 * <p>记录登录失败尝试，用于防止暴力破解攻击
 *
 * @author YoungerYang-Y
 */
@Data
@Entity
public class AuthFailedAttempt {

    /** 尝试ID（主键） */
    private Long attemptId;

    /** 用户ID（可为空） */
    private Long userId;

    /** 凭证类型 */
    private CredentialType credentialType;

    /** 尝试的凭证值（用户名/手机号/邮箱等） */
    private String credentialValue;

    /** IP地址 */
    private String ipAddress;

    /** 用户代理 */
    private String userAgent;

    /** 设备指纹 */
    private String deviceFingerprint;

    /** 失败原因 */
    private String failReason;

    /** 尝试时间 */
    private LocalDateTime attemptTime;

    /** 创建失败尝试记录 */
    public static AuthFailedAttempt create(
            Long userId,
            CredentialType credentialType,
            String credentialValue,
            String ipAddress,
            String userAgent,
            String deviceFingerprint,
            String failReason) {
        AuthFailedAttempt attempt = new AuthFailedAttempt();
        attempt.setUserId(userId);
        attempt.setCredentialType(credentialType);
        attempt.setCredentialValue(credentialValue);
        attempt.setIpAddress(ipAddress);
        attempt.setUserAgent(userAgent);
        attempt.setDeviceFingerprint(deviceFingerprint);
        attempt.setFailReason(failReason);
        attempt.setAttemptTime(LocalDateTime.now());
        return attempt;
    }
}
