package com.yggdrasil.labs.domain.auth.model;

import java.time.LocalDateTime;

import com.alibaba.cola.domain.Entity;
import com.yggdrasil.labs.domain.auth.model.enums.MfaType;

import lombok.Data;

/**
 * 多因子认证实体
 *
 * <p>支持 TOTP、短信验证码、邮箱验证码、U2F Key 配置
 *
 * @author YoungerYang-Y
 */
@Data
@Entity
public class AuthMfa {

    /** MFA ID（主键，雪花ID） */
    private Long mfaId;

    /** 用户ID（关联用户服务） */
    private Long userId;

    /** MFA类型 */
    private MfaType mfaType;

    /** MFA名称（如：Google Authenticator、备用手机号等） */
    private String mfaName;

    /** 密钥（TOTP密钥、U2F Key等，加密存储） */
    private String secretKey;

    /** 手机号（用于短信验证） */
    private String phoneNumber;

    /** 邮箱（用于邮箱验证） */
    private String email;

    /** U2F Key Handle */
    private String u2fKeyHandle;

    /** U2F 公钥 */
    private String u2fPublicKey;

    /** 备用验证码（JSON数组，加密存储） */
    private String backupCodes;

    /** 是否启用 */
    private Boolean isEnabled;

    /** 是否默认MFA方式 */
    private Boolean isDefault;

    /** 最后使用时间 */
    private LocalDateTime lastUsedAt;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;

    /** 创建MFA配置 */
    public static AuthMfa create(Long userId, MfaType mfaType, String mfaName) {
        AuthMfa mfa = new AuthMfa();
        mfa.setUserId(userId);
        mfa.setMfaType(mfaType);
        mfa.setMfaName(mfaName);
        mfa.setIsEnabled(false);
        mfa.setIsDefault(false);
        mfa.setCreateTime(LocalDateTime.now());
        mfa.setUpdateTime(LocalDateTime.now());
        return mfa;
    }

    /** 启用MFA */
    public void enable() {
        this.isEnabled = true;
        this.updateTime = LocalDateTime.now();
    }

    /** 禁用MFA */
    public void disable() {
        this.isEnabled = false;
        this.updateTime = LocalDateTime.now();
    }

    /** 设置为默认MFA方式 */
    public void setAsDefault() {
        this.isDefault = true;
        this.updateTime = LocalDateTime.now();
    }

    /** 记录使用时间 */
    public void recordUsage() {
        this.lastUsedAt = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
    }
}
