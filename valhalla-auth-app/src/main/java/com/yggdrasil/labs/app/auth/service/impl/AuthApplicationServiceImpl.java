package com.yggdrasil.labs.app.auth.service.impl;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import com.yggdrasil.labs.app.auth.dto.cmd.CreateCredentialCmd;
import com.yggdrasil.labs.app.auth.dto.cmd.DeleteCredentialCmd;
import com.yggdrasil.labs.app.auth.dto.cmd.InitializeUserCmd;
import com.yggdrasil.labs.app.auth.dto.cmd.LoginCmd;
import com.yggdrasil.labs.app.auth.dto.cmd.LogoutCmd;
import com.yggdrasil.labs.app.auth.dto.cmd.RefreshTokenCmd;
import com.yggdrasil.labs.app.auth.dto.cmd.VerifyTokenCmd;
import com.yggdrasil.labs.app.auth.dto.co.AuthUserCO;
import com.yggdrasil.labs.app.auth.dto.co.CredentialCO;
import com.yggdrasil.labs.app.auth.dto.co.LoginResultCO;
import com.yggdrasil.labs.app.auth.dto.co.TokenCO;
import com.yggdrasil.labs.app.auth.dto.co.UserInitializationCO;
import com.yggdrasil.labs.app.auth.dto.query.GetTokenQuery;
import com.yggdrasil.labs.app.auth.dto.query.GetUserQuery;
import com.yggdrasil.labs.app.auth.dto.query.ListCredentialsQuery;
import com.yggdrasil.labs.app.auth.executor.CreateCredentialExecutor;
import com.yggdrasil.labs.app.auth.executor.DeleteCredentialExecutor;
import com.yggdrasil.labs.app.auth.executor.InitializeUserExecutor;
import com.yggdrasil.labs.app.auth.executor.LoginExecutor;
import com.yggdrasil.labs.app.auth.executor.LogoutExecutor;
import com.yggdrasil.labs.app.auth.executor.RefreshTokenExecutor;
import com.yggdrasil.labs.app.auth.executor.VerifyTokenExecutor;
import com.yggdrasil.labs.app.auth.query.AuthQuery;
import com.yggdrasil.labs.app.auth.service.AuthApplicationService;

/**
 * 认证应用服务实现
 *
 * <p>实现 AuthApplicationService 接口，委托给对应的 Executor 和 Query 执行业务逻辑
 *
 * @author YoungerYang-Y
 */
@Service
public class AuthApplicationServiceImpl implements AuthApplicationService {

    @Resource private LoginExecutor loginExecutor;
    @Resource private RefreshTokenExecutor refreshTokenExecutor;
    @Resource private LogoutExecutor logoutExecutor;
    @Resource private VerifyTokenExecutor verifyTokenExecutor;
    @Resource private CreateCredentialExecutor createCredentialExecutor;
    @Resource private DeleteCredentialExecutor deleteCredentialExecutor;
    @Resource private InitializeUserExecutor initializeUserExecutor;
    @Resource private AuthQuery authQuery;

    @Override
    public SingleResponse<LoginResultCO> login(LoginCmd cmd) {
        return loginExecutor.execute(cmd);
    }

    @Override
    public SingleResponse<TokenCO> refreshToken(RefreshTokenCmd cmd) {
        return refreshTokenExecutor.execute(cmd);
    }

    @Override
    public Response logout(LogoutCmd cmd) {
        return logoutExecutor.execute(cmd);
    }

    @Override
    public SingleResponse<AuthUserCO> verifyToken(VerifyTokenCmd cmd) {
        return verifyTokenExecutor.execute(cmd);
    }

    @Override
    public Response createCredential(CreateCredentialCmd cmd) {
        return createCredentialExecutor.execute(cmd);
    }

    @Override
    public Response deleteCredential(DeleteCredentialCmd cmd) {
        return deleteCredentialExecutor.execute(cmd);
    }

    @Override
    public SingleResponse<AuthUserCO> getUser(GetUserQuery query) {
        // TODO: 用户信息现在应该从用户服务获取，而不是从认证服务
        // 认证服务不再存储用户信息
        return SingleResponse.buildFailure("NOT_IMPLEMENTED", "用户信息查询功能已移除，请使用用户服务");
    }

    @Override
    public MultiResponse<CredentialCO> listCredentials(ListCredentialsQuery query) {
        return authQuery.listCredentials(query);
    }

    @Override
    public SingleResponse<TokenCO> getToken(GetTokenQuery query) {
        // TODO: Token 信息现在存储在 Redis 中，需要从 Redis 查询
        // 实现从 Redis 查询 Token 信息的逻辑
        return SingleResponse.buildFailure("NOT_IMPLEMENTED", "Token 查询功能需要从 Redis 实现");
    }

    @Override
    public SingleResponse<UserInitializationCO> initializeUser(InitializeUserCmd cmd) {
        return initializeUserExecutor.execute(cmd);
    }
}
