package com.yggdrasil.labs.domain.auth.model;

import java.time.LocalDateTime;

import com.alibaba.cola.domain.Entity;
import com.yggdrasil.labs.domain.auth.model.enums.CredentialType;
import com.yggdrasil.labs.domain.auth.model.enums.LoginStatus;
import com.yggdrasil.labs.domain.auth.model.enums.LoginType;
import com.yggdrasil.labs.domain.auth.model.enums.MfaType;

import lombok.Data;

/**
 * 登录日志实体
 *
 * <p>记录所有登录尝试（成功/失败）
 *
 * @author YoungerYang-Y
 */
@Data
@Entity
public class AuthLoginLog {

    /** 日志ID（主键） */
    private Long logId;

    /** 用户ID（关联用户服务，全局唯一） */
    private Long userId;

    /** 登录凭证类型 */
    private CredentialType credentialType;

    /** 登录凭证值（脱敏处理） */
    private String credentialValue;

    /** 登录类型 */
    private LoginType loginType;

    /** 登录状态 */
    private LoginStatus loginStatus;

    /** 是否使用了MFA */
    private Boolean mfaUsed;

    /** 使用的MFA类型 */
    private MfaType mfaType;

    /** 失败原因 */
    private String failReason;

    /** 登录IP */
    private String loginIp;

    /** 用户代理（User-Agent） */
    private String userAgent;

    /** 设备类型：WEB, MOBILE, API */
    private String deviceType;

    /** 设备ID */
    private String deviceId;

    /** 登录地点（根据IP解析） */
    private String location;

    /** 登录时间 */
    private LocalDateTime loginTime;

    /** 创建登录日志 */
    public static AuthLoginLog create(
            Long userId,
            CredentialType credentialType,
            String credentialValue,
            LoginType loginType,
            LoginStatus loginStatus,
            String loginIp,
            String userAgent,
            String deviceType,
            String deviceId,
            String location) {
        AuthLoginLog log = new AuthLoginLog();
        log.setUserId(userId);
        log.setCredentialType(credentialType);
        log.setCredentialValue(credentialValue);
        log.setLoginType(loginType);
        log.setLoginStatus(loginStatus);
        log.setLoginIp(loginIp);
        log.setUserAgent(userAgent);
        log.setDeviceType(deviceType);
        log.setDeviceId(deviceId);
        log.setLocation(location);
        log.setMfaUsed(false);
        log.setLoginTime(LocalDateTime.now());
        return log;
    }

    /** 记录MFA使用情况 */
    public void recordMfa(MfaType mfaType) {
        this.mfaUsed = true;
        this.mfaType = mfaType;
    }

    /** 记录失败原因 */
    public void recordFailure(String reason) {
        this.loginStatus = LoginStatus.FAILED;
        this.failReason = reason;
    }
}
