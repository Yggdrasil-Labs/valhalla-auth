package com.yggdrasil.labs.client.dto.cmd;

import java.io.Serializable;

import com.yggdrasil.labs.client.dto.enums.RpcCredentialTypeEnum;

import lombok.Data;

/**
 * 创建凭证命令（RPC 接口用）
 *
 * @author YoungerYang-Y
 */
@Data
public class RpcCreateCredentialCmd implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 用户ID */
    private Long userId;

    /** 凭证类型 */
    private RpcCredentialTypeEnum credentialType;

    /** 凭证值（用户名/手机号/邮箱/三方ID） */
    private String credentialValue;

    /** 三方登录的唯一ID（仅三方登录时使用） */
    private String thirdPartyId;

    /** 三方登录名称（如：wechat, google，仅三方登录时使用） */
    private String thirdPartyName;

    /** 是否主凭证（默认false） */
    private Boolean isPrimary = false;
}
