package com.yggdrasil.labs.app.auth.dto.enums;

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

    /** Token已过期 */
    TOKEN_EXPIRED("TOKEN_EXPIRED", "令牌已过期"),

    /** Token无效 */
    TOKEN_INVALID("TOKEN_INVALID", "令牌无效"),

    /** Token已吊销 */
    TOKEN_REVOKED("TOKEN_REVOKED", "令牌已吊销"),

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
    VERIFY_TOKEN_NOT_IMPLEMENTED("VERIFY_TOKEN_NOT_IMPLEMENTED", "Token 验证功能待实现"),

    /** 无效的凭证类型 */
    INVALID_CREDENTIAL_TYPE("INVALID_CREDENTIAL_TYPE", "无效的凭证类型"),

    /** OAuth 场景必须提供 provider */
    PROVIDER_REQUIRED("PROVIDER_REQUIRED", "OAuth 场景必须提供 provider"),

    /** 账号已锁定 */
    ACCOUNT_LOCKED("ACCOUNT_LOCKED", "账号已锁定"),

    /** 账号已被禁用 */
    ACCOUNT_DISABLED("ACCOUNT_DISABLED", "账号已被禁用"),

    /** Redis 不可用 */
    REDIS_UNAVAILABLE("REDIS_UNAVAILABLE", "服务暂时不可用，请稍后重试");

    private final String errCode;
    private final String errDesc;

    AuthErrorCode(String errCode, String errDesc) {
        this.errCode = errCode;
        this.errDesc = errDesc;
    }
}
