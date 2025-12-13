package com.yggdrasil.labs.adapter.web.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.yggdrasil.labs.client.dto.enums.CredentialTypeEnum;

import lombok.Data;

/**
 * 登录请求
 *
 * @author YoungerYang-Y
 */
@Data
public class LoginRequest {

    /** 凭证类型 */
    @NotNull(message = "凭证类型不能为空")
    private CredentialTypeEnum credentialType;

    /** 凭证值（用户名/手机号/邮箱） */
    @NotBlank(message = "凭证值不能为空")
    private String credentialValue;

    /** 密码 */
    @NotBlank(message = "密码不能为空")
    private String password;

    /** 设备ID（可选） */
    private String deviceId;

    /** 设备类型（可选） */
    private String deviceType;

    /** 设备名称（可选） */
    private String deviceName;

    /** IP地址（可选） */
    private String ipAddress;

    /** 用户代理（可选） */
    private String userAgent;
}
