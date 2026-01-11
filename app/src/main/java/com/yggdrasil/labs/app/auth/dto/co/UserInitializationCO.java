package com.yggdrasil.labs.app.auth.dto.co;

import com.alibaba.cola.dto.DTO;
import com.yggdrasil.labs.app.auth.dto.enums.CredentialTypeEnum;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户初始化结果对象
 *
 * <p>包含用户初始化后的凭证信息和初始密码（如适用）
 *
 * @author YoungerYang-Y
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserInitializationCO extends DTO {

    /** 凭证ID */
    private Long credentialId;

    /** 凭证类型 */
    private CredentialTypeEnum credentialType;

    /** 凭证值 */
    private String credentialValue;

    /** OAuth 提供方（仅 OAuth 场景） */
    private String provider;

    /** 初始密码（仅非 OAuth 场景，仅返回一次） */
    private String initialPassword;

    /** 是否强制改密（仅非 OAuth 场景，固定为 true） */
    private Boolean forceChangePassword;
}
