package com.yggdrasil.labs.adapter.web.controller;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import com.yggdrasil.labs.adapter.web.convert.AuthWebConverter;
import com.yggdrasil.labs.adapter.web.request.CreateCredentialRequest;
import com.yggdrasil.labs.adapter.web.request.GetTokenRequest;
import com.yggdrasil.labs.adapter.web.request.GetUserRequest;
import com.yggdrasil.labs.adapter.web.request.ListCredentialsRequest;
import com.yggdrasil.labs.adapter.web.request.LoginRequest;
import com.yggdrasil.labs.adapter.web.request.LogoutRequest;
import com.yggdrasil.labs.adapter.web.request.RefreshTokenRequest;
import com.yggdrasil.labs.adapter.web.request.VerifyTokenRequest;
import com.yggdrasil.labs.client.api.AuthClient;
import com.yggdrasil.labs.client.dto.cmd.DeleteCredentialCmd;
import com.yggdrasil.labs.client.dto.co.AuthUserCO;
import com.yggdrasil.labs.client.dto.co.CredentialCO;
import com.yggdrasil.labs.client.dto.co.LoginResultCO;
import com.yggdrasil.labs.client.dto.co.TokenCO;

/**
 * 认证服务 REST 控制器
 *
 * <p>提供认证相关的 HTTP API 接口
 *
 * @author YoungerYang-Y
 */
@RestController
@RequestMapping("/api/v1/auth")
@Validated
public class AuthController {

    @Resource private AuthClient authClient;
    @Resource private AuthWebConverter authWebConverter;

    /**
     * 用户登录
     *
     * @param request 登录请求
     * @return 登录结果（包含 Token 和用户信息）
     */
    @PostMapping("/login")
    public SingleResponse<LoginResultCO> login(@Valid @RequestBody LoginRequest request) {
        return authClient.login(authWebConverter.toLoginCmd(request));
    }

    /**
     * 刷新 Token
     *
     * @param request Token 刷新请求
     * @return Token 信息
     */
    @PostMapping("/refresh")
    public SingleResponse<TokenCO> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        return authClient.refreshToken(authWebConverter.toRefreshTokenCmd(request));
    }

    /**
     * 用户登出
     *
     * @param request 登出请求
     * @return 操作结果
     */
    @PostMapping("/logout")
    public Response logout(@Valid @RequestBody LogoutRequest request) {
        return authClient.logout(authWebConverter.toLogoutCmd(request));
    }

    /**
     * 验证 Token
     *
     * @param request Token 验证请求
     * @return 验证结果（包含用户ID等信息）
     */
    @PostMapping("/verify")
    public SingleResponse<AuthUserCO> verifyToken(@Valid @RequestBody VerifyTokenRequest request) {
        return authClient.verifyToken(authWebConverter.toVerifyTokenCmd(request));
    }

    /**
     * 创建凭证
     *
     * @param request 创建凭证请求
     * @return 操作结果
     */
    @PostMapping("/credentials")
    public Response createCredential(@Valid @RequestBody CreateCredentialRequest request) {
        return authClient.createCredential(authWebConverter.toCreateCredentialCmd(request));
    }

    /**
     * 删除凭证
     *
     * @param credentialId 凭证ID
     * @return 操作结果
     */
    @DeleteMapping("/credentials/{credentialId}")
    public Response deleteCredential(
            @PathVariable @NotNull(message = "凭证ID不能为空") Long credentialId) {
        DeleteCredentialCmd cmd = new DeleteCredentialCmd();
        cmd.setCredentialId(credentialId);
        return authClient.deleteCredential(cmd);
    }

    /**
     * 查询用户信息
     *
     * @param userId 用户ID
     * @return 用户认证信息
     */
    @GetMapping("/users/{userId}")
    public SingleResponse<AuthUserCO> getUser(
            @PathVariable @NotNull(message = "用户ID不能为空") Long userId) {
        GetUserRequest request = new GetUserRequest();
        request.setUserId(userId);
        return authClient.getUser(authWebConverter.toGetUserQuery(request));
    }

    /**
     * 查询凭证列表
     *
     * @param userId 用户ID
     * @return 凭证列表
     */
    @GetMapping("/users/{userId}/credentials")
    public MultiResponse<CredentialCO> listCredentials(
            @PathVariable @NotNull(message = "用户ID不能为空") Long userId) {
        ListCredentialsRequest request = new ListCredentialsRequest();
        request.setUserId(userId);
        return authClient.listCredentials(authWebConverter.toListCredentialsQuery(request));
    }

    /**
     * 查询 Token 信息
     *
     * @param tokenId Token ID（可以是 tokenHash 或 jwtId）
     * @return Token 信息
     */
    @GetMapping("/tokens/{tokenId}")
    public SingleResponse<TokenCO> getToken(@PathVariable String tokenId) {
        GetTokenRequest request = new GetTokenRequest();
        // 根据 tokenId 的格式判断是 tokenHash 还是 jwtId
        // 这里简化处理，假设是 jwtId，实际可以根据格式判断
        request.setJwtId(tokenId);
        return authClient.getToken(authWebConverter.toGetTokenQuery(request));
    }
}
