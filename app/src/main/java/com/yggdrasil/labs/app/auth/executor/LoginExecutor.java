package com.yggdrasil.labs.app.auth.executor;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.cola.dto.SingleResponse;
import com.yggdrasil.labs.app.auth.assembler.AuthAssembler;
import com.yggdrasil.labs.app.auth.convert.AuthConverter;
import com.yggdrasil.labs.app.auth.dto.cmd.LoginCmd;
import com.yggdrasil.labs.app.auth.dto.co.LoginResultCO;
import com.yggdrasil.labs.app.auth.dto.enums.AuthErrorCode;
import com.yggdrasil.labs.domain.auth.model.AuthCredential;
import com.yggdrasil.labs.domain.auth.model.AuthPassword;
import com.yggdrasil.labs.domain.auth.model.enums.CredentialType;
import com.yggdrasil.labs.domain.auth.repository.AuthCredentialRepository;
import com.yggdrasil.labs.domain.auth.repository.AuthPasswordRepository;

/**
 * 登录用例执行器
 *
 * @author YoungerYang-Y
 */
@Component
public class LoginExecutor {

    @Resource private AuthCredentialRepository authCredentialRepository;
    @Resource private AuthPasswordRepository authPasswordRepository;
    @Resource private AuthAssembler authAssembler;
    @Resource private AuthConverter authConverter;

    /** 执行登录用例 */
    @Transactional(rollbackFor = Exception.class)
    public SingleResponse<LoginResultCO> execute(LoginCmd cmd) {
        // 1. 根据凭证类型和凭证值查找凭证
        CredentialType credentialType =
                authConverter.toDomainCredentialType(cmd.getCredentialType());
        AuthCredential credential =
                authCredentialRepository.findByTypeAndValue(
                        credentialType, cmd.getCredentialValue());
        if (credential == null) {
            return SingleResponse.buildFailure(
                    AuthErrorCode.CREDENTIAL_NOT_FOUND.getErrCode(),
                    AuthErrorCode.CREDENTIAL_NOT_FOUND.getErrDesc());
        }

        // 2. 查找用户密码信息
        AuthPassword authPassword = authPasswordRepository.findByUserId(credential.getUserId());
        if (authPassword == null) {
            return SingleResponse.buildFailure(
                    AuthErrorCode.PASSWORD_NOT_SET.getErrCode(),
                    AuthErrorCode.PASSWORD_NOT_SET.getErrDesc());
        }

        // 3. 检查密码状态
        if (!authPassword.isValid()) {
            return SingleResponse.buildFailure(
                    AuthErrorCode.ACCOUNT_UNAVAILABLE.getErrCode(),
                    AuthErrorCode.ACCOUNT_UNAVAILABLE.getErrDesc());
        }

        // 4. 验证密码
        // TODO: 实现密码验证逻辑（BCrypt/Argon2id/PBKDF2）
        // if (!passwordService.matches(cmd.getPassword(), authPassword.getPasswordHash(),
        // authPassword.getPasswordAlgo())) {
        //     return SingleResponse.buildFailure(
        //             AuthErrorCode.PASSWORD_INCORRECT.getErrCode(),
        //             AuthErrorCode.PASSWORD_INCORRECT.getErrDesc());
        // }

        // 5. 生成 Token
        // TODO: 实现 JWT Token 生成逻辑
        // TokenCO tokenCO = jwtService.generateToken(authUser, cmd);

        // 6. 保存 Token 记录
        // TODO: 保存 Token 到数据库和 Redis

        // 7. 更新密码最后使用时间（如果需要）
        // TODO: 如果需要记录最后登录时间，可以通过其他方式实现（如 Redis 或用户服务）

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
