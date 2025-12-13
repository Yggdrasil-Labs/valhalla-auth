package com.yggdrasil.labs.app.auth.executor;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.cola.dto.SingleResponse;
import com.yggdrasil.labs.app.auth.assembler.AuthAssembler;
import com.yggdrasil.labs.app.auth.convert.AuthConverter;
import com.yggdrasil.labs.client.dto.cmd.LoginCmd;
import com.yggdrasil.labs.client.dto.co.LoginResultCO;
import com.yggdrasil.labs.client.dto.enums.AuthErrorCode;
import com.yggdrasil.labs.domain.auth.model.AuthCredential;
import com.yggdrasil.labs.domain.auth.model.AuthUser;
import com.yggdrasil.labs.domain.auth.model.enums.CredentialType;
import com.yggdrasil.labs.domain.auth.repository.AuthCredentialRepository;
import com.yggdrasil.labs.domain.auth.repository.AuthTokenRepository;
import com.yggdrasil.labs.domain.auth.repository.AuthUserRepository;

/**
 * 登录用例执行器
 *
 * @author YoungerYang-Y
 */
@Component
public class LoginExecutor {

    @Resource private AuthUserRepository authUserRepository;
    @Resource private AuthCredentialRepository authCredentialRepository;
    @Resource private AuthTokenRepository authTokenRepository;
    @Resource private AuthAssembler authAssembler;

    /** 执行登录用例 */
    @Transactional(rollbackFor = Exception.class)
    public SingleResponse<LoginResultCO> execute(LoginCmd cmd) {
        // 1. 根据凭证类型和凭证值查找凭证
        CredentialType credentialType =
                AuthConverter.INSTANCE.toDomainCredentialType(cmd.getCredentialType());
        AuthCredential credential =
                authCredentialRepository.findByTypeAndValue(
                        credentialType, cmd.getCredentialValue());
        if (credential == null) {
            return SingleResponse.buildFailure(
                    AuthErrorCode.CREDENTIAL_NOT_FOUND.getErrCode(),
                    AuthErrorCode.CREDENTIAL_NOT_FOUND.getErrDesc());
        }

        // 2. 查找用户认证信息
        AuthUser authUser = authUserRepository.findByUserId(credential.getUserId());
        if (authUser == null) {
            return SingleResponse.buildFailure(
                    AuthErrorCode.USER_NOT_FOUND.getErrCode(),
                    AuthErrorCode.USER_NOT_FOUND.getErrDesc());
        }

        // 3. 检查账户状态
        if (!authUser.isAvailable()) {
            return SingleResponse.buildFailure(
                    AuthErrorCode.ACCOUNT_UNAVAILABLE.getErrCode(),
                    AuthErrorCode.ACCOUNT_UNAVAILABLE.getErrDesc());
        }

        // 4. 验证密码
        // TODO: 实现密码验证逻辑（BCrypt）
        if (authUser.getPasswordHash() == null) {
            return SingleResponse.buildFailure(
                    AuthErrorCode.PASSWORD_NOT_SET.getErrCode(),
                    AuthErrorCode.PASSWORD_NOT_SET.getErrDesc());
        }
        // if (!passwordService.matches(cmd.getPassword(), authUser.getPasswordHash())) {
        //     return SingleResponse.buildFailure(
        //             AuthErrorCode.PASSWORD_INCORRECT.getErrCode(),
        // AuthErrorCode.PASSWORD_INCORRECT.getErrDesc());
        // }

        // 5. 生成 Token
        // TODO: 实现 JWT Token 生成逻辑
        // TokenCO tokenCO = jwtService.generateToken(authUser, cmd);

        // 6. 保存 Token 记录
        // TODO: 保存 Token 到数据库和 Redis

        // 7. 更新用户登录信息
        authUser.recordLogin(cmd.getIpAddress());
        authUserRepository.update(authUser);

        // 8. 组装返回结果
        // TODO: 返回完整的 LoginResultCO
        // LoginResultCO result = authAssembler.toLoginResultCO(authUser, tokenCO);
        // return SingleResponse.of(result);

        // 临时返回，待实现完整逻辑
        return SingleResponse.buildFailure(
                AuthErrorCode.LOGIN_NOT_IMPLEMENTED.getErrCode(),
                AuthErrorCode.LOGIN_NOT_IMPLEMENTED.getErrDesc());
    }
}
