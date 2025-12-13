package com.yggdrasil.labs.client.dto.enums;

import lombok.Getter;

/**
 * 认证服务错误码枚举
 *
 * <p>定义认证服务相关的错误码
 *
 * @author YoungerYang-Y
 */
@Getter
public enum AuthErrorCode {

    /** 用户不存在 */
    USER_NOT_FOUND("USER_NOT_FOUND", "用户不存在"),

    /** 凭证不存在 */
    CREDENTIAL_NOT_FOUND("CREDENTIAL_NOT_FOUND", "凭证不存在"),

    /** 凭证已存在 */
    CREDENTIAL_ALREADY_EXISTS("CREDENTIAL_ALREADY_EXISTS", "凭证已存在"),

    /** 不能删除主凭证 */
    CANNOT_DELETE_PRIMARY_CREDENTIAL("CANNOT_DELETE_PRIMARY_CREDENTIAL", "不能删除主凭证"),

    /** Token不存在 */
    TOKEN_NOT_FOUND("TOKEN_NOT_FOUND", "Token不存在"),

    /** 账户不可用 */
    ACCOUNT_UNAVAILABLE("ACCOUNT_UNAVAILABLE", "账户不可用"),

    /** 密码未设置 */
    PASSWORD_NOT_SET("PASSWORD_NOT_SET", "密码未设置"),

    /** 密码错误 */
    PASSWORD_INCORRECT("PASSWORD_INCORRECT", "密码错误"),

    /** 登录功能待实现 */
    LOGIN_NOT_IMPLEMENTED("LOGIN_NOT_IMPLEMENTED", "登录功能待实现"),

    /** Token 刷新功能待实现 */
    REFRESH_TOKEN_NOT_IMPLEMENTED("REFRESH_TOKEN_NOT_IMPLEMENTED", "Token 刷新功能待实现"),

    /** Token 验证功能待实现 */
    VERIFY_TOKEN_NOT_IMPLEMENTED("VERIFY_TOKEN_NOT_IMPLEMENTED", "Token 验证功能待实现");

    private final String errCode;
    private final String errDesc;

    AuthErrorCode(String errCode, String errDesc) {
        this.errCode = errCode;
        this.errDesc = errDesc;
    }
}
