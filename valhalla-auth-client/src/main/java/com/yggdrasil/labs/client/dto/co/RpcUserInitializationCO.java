package com.yggdrasil.labs.client.dto.co;

import java.io.Serializable;

import com.yggdrasil.labs.client.dto.enums.RpcCredentialTypeEnum;

import lombok.Data;

/**
 * 用户初始化结果对象（RPC 接口用）
 *
 * <p>包含用户初始化后的凭证信息和初始密码（如适用）
 *
 * @author YoungerYang-Y
 */
@Data
public class RpcUserInitializationCO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 凭证ID */
    private Long credentialId;

    /** 凭证类型 */
    private RpcCredentialTypeEnum credentialType;

    /** 凭证值 */
    private String credentialValue;

    /** OAuth 提供方（仅 OAuth 场景） */
    private String provider;

    /** 初始密码（仅非 OAuth 场景，仅返回一次） */
    private String initialPassword;

    /** 是否强制改密（仅非 OAuth 场景，固定为 true） */
    private Boolean forceChangePassword;
}
