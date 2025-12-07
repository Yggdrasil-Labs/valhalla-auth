package com.yggdrasil.labs.domain.auth.model;

import java.time.LocalDateTime;

import com.alibaba.cola.domain.Entity;
import com.yggdrasil.labs.domain.auth.model.enums.CredentialType;

import lombok.Data;

/**
 * 登录凭证实体
 *
 * <p>支持多种登录方式：用户名、手机号、邮箱、三方登录（微信/Google等）
 *
 * @author YoungerYang-Y
 */
@Data
@Entity
public class AuthCredential {

    /** 凭证ID（主键，雪花ID） */
    private Long credentialId;

    /** 用户ID（关联用户服务，全局唯一） */
    private Long userId;

    /** 凭证类型 */
    private CredentialType credentialType;

    /** 凭证值（用户名/手机号/邮箱/三方ID） */
    private String credentialValue;

    /** 三方登录的唯一ID（仅三方登录时使用） */
    private String thirdPartyId;

    /** 三方登录名称（如：wechat, google） */
    private String thirdPartyName;

    /** 是否主凭证 */
    private Boolean isPrimary;

    /** 是否已验证 */
    private Boolean verified;

    /** 验证时间 */
    private LocalDateTime verifiedAt;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;

    /** 创建凭证 */
    public static AuthCredential create(
            Long userId, CredentialType credentialType, String credentialValue) {
        AuthCredential credential = new AuthCredential();
        credential.setUserId(userId);
        credential.setCredentialType(credentialType);
        credential.setCredentialValue(credentialValue);
        credential.setIsPrimary(false);
        credential.setVerified(false);
        credential.setCreateTime(LocalDateTime.now());
        credential.setUpdateTime(LocalDateTime.now());
        return credential;
    }

    /** 创建三方登录凭证 */
    public static AuthCredential createThirdParty(
            Long userId,
            CredentialType credentialType,
            String thirdPartyId,
            String thirdPartyName) {
        AuthCredential credential = create(userId, credentialType, thirdPartyId);
        credential.setThirdPartyId(thirdPartyId);
        credential.setThirdPartyName(thirdPartyName);
        return credential;
    }

    /** 设置为主凭证 */
    public void setAsPrimary() {
        this.isPrimary = true;
        this.updateTime = LocalDateTime.now();
    }

    /** 验证凭证 */
    public void verify() {
        this.verified = true;
        this.verifiedAt = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
    }

    /** 是否为三方登录凭证 */
    public boolean isThirdParty() {
        return credentialType != null && credentialType.isThirdParty();
    }
}
