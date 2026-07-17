package com.yggdrasil.labs.app.auth.service;

/**
 * Token 服务接口
 *
 * <p>定义 Token 完整生命周期管理方法，由 infrastructure 层实现
 */
public interface TokenService {

    /**
     * 签发令牌对（Access Token + Refresh Token）
     *
     * @param userId 用户ID
     * @return Token 对结果
     */
    TokenPairResult issueTokenPair(Long userId);

    /**
     * 验证访问令牌
     *
     * @param accessToken 访问令牌
     * @return 验证结果
     */
    VerifyTokenResult verifyAccessToken(String accessToken);

    /**
     * 刷新访问令牌
     *
     * @param refreshToken 刷新令牌
     * @return 刷新结果
     */
    RefreshTokenResult refreshAccessToken(String refreshToken);

    /**
     * 吊销当前会话令牌
     *
     * @param accessToken 访问令牌
     */
    void revokeToken(String accessToken);

    /**
     * 吊销用户所有会话令牌
     *
     * @param userId 用户ID
     */
    void revokeAllTokens(Long userId);
}
