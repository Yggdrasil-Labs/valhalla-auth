package com.yggdrasil.labs.app.auth.dto.cmd;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.alibaba.cola.dto.Command;
import com.yggdrasil.labs.app.auth.dto.enums.CredentialTypeEnum;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户初始化命令
 *
 * <p>用于初始化用户认证凭证和初始密码（如适用），支持多种用户创建场景：
 *
 * <ul>
 *   <li>管理员创建：USERNAME + 密码
 *   <li>用户密码注册：USERNAME/PHONE/EMAIL + 密码
 *   <li>电话短信注册：PHONE + 验证状态 + 密码（可选）
 *   <li>邮箱注册：EMAIL + 验证状态 + 密码（可选）
 *   <li>第三方 OAuth 登录：WECHAT/GOOGLE/OTHER + provider（不需要密码）
 * </ul>
 *
 * @author YoungerYang-Y
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class InitializeUserCmd extends Command {

    /** 用户ID */
    @NotNull(message = "用户ID不能为空")
    private Long userId;

    /** 凭证类型 */
    @NotNull(message = "凭证类型不能为空")
    private CredentialTypeEnum credentialType;

    /** 凭证值（用户名/手机号/邮箱/OAuth ID） */
    @NotBlank(message = "凭证值不能为空")
    private String credentialValue;

    /** 初始密码（可选，OAuth 场景不需要） */
    private String initialPassword;

    /** OAuth 提供方（可选，仅 OAuth 场景需要，如：wechat, google, github） */
    private String provider;

    /** 是否已验证（可选，用于设置凭证验证状态） */
    private Boolean verified;

    /** 是否主凭证（可选，默认 false） */
    private Boolean isPrimary = false;
}
