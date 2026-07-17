package com.yggdrasil.labs.app.auth.service;

import lombok.Getter;

/**
 * Token 服务异常
 *
 * <p>携带错误码的运行时异常
 */
@Getter
public class TokenServiceException extends RuntimeException {

    private final String errorCode;

    public TokenServiceException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public static TokenServiceException tokenExpired() {
        return new TokenServiceException("TOKEN_EXPIRED", "令牌已过期");
    }

    public static TokenServiceException tokenInvalid() {
        return new TokenServiceException("TOKEN_INVALID", "令牌无效");
    }

    public static TokenServiceException tokenRevoked() {
        return new TokenServiceException("TOKEN_REVOKED", "令牌已吊销");
    }

    public static TokenServiceException redisUnavailable() {
        return new TokenServiceException("REDIS_UNAVAILABLE", "服务暂时不可用，请稍后重试");
    }
}
