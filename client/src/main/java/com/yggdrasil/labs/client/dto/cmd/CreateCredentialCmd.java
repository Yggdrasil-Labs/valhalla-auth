package com.yggdrasil.labs.client.dto.cmd;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.alibaba.cola.dto.Command;
import com.yggdrasil.labs.client.dto.enums.CredentialTypeEnum;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 创建凭证命令
 *
 * @author YoungerYang-Y
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CreateCredentialCmd extends Command {

    /** 用户ID */
    @NotNull(message = "用户ID不能为空")
    private Long userId;

    /** 凭证类型 */
    @NotNull(message = "凭证类型不能为空")
    private CredentialTypeEnum credentialType;

    /** 凭证值（用户名/手机号/邮箱/三方ID） */
    @NotBlank(message = "凭证值不能为空")
    private String credentialValue;

    /** 三方登录的唯一ID（仅三方登录时使用） */
    private String thirdPartyId;

    /** 三方登录名称（如：wechat, google，仅三方登录时使用） */
    private String thirdPartyName;

    /** 是否主凭证（默认false） */
    private Boolean isPrimary = false;
}

