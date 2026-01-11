package com.yggdrasil.labs.app.auth.executor;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.cola.dto.SingleResponse;
import com.yggdrasil.labs.app.auth.assembler.AuthAssembler;
import com.yggdrasil.labs.app.auth.convert.AuthConverter;
import com.yggdrasil.labs.app.auth.dto.cmd.InitializeUserCmd;
import com.yggdrasil.labs.app.auth.dto.co.UserInitializationCO;
import com.yggdrasil.labs.app.auth.dto.enums.AuthErrorCode;
import com.yggdrasil.labs.app.auth.service.PasswordGeneratorService;
import com.yggdrasil.labs.app.auth.service.PasswordService;
import com.yggdrasil.labs.domain.auth.model.AuthCredential;
import com.yggdrasil.labs.domain.auth.model.AuthPassword;
import com.yggdrasil.labs.domain.auth.model.enums.CredentialType;
import com.yggdrasil.labs.domain.auth.repository.AuthCredentialRepository;
import com.yggdrasil.labs.domain.auth.repository.AuthPasswordRepository;

/**
 * 用户初始化用例执行器
 *
 * <p>负责初始化用户认证凭证和初始密码（如适用）
 *
 * @author YoungerYang-Y
 */
@Component
public class InitializeUserExecutor {

    @Resource private AuthCredentialRepository authCredentialRepository;
    @Resource private AuthPasswordRepository authPasswordRepository;
    @Resource private PasswordGeneratorService passwordGeneratorService;
    @Resource private PasswordService passwordService;
    @Resource private AuthAssembler authAssembler;
    @Resource private AuthConverter authConverter;

    /** 执行用户初始化用例 */
    @Transactional(rollbackFor = Exception.class)
    public SingleResponse<UserInitializationCO> execute(InitializeUserCmd cmd) {
        CredentialType credentialType =
                authConverter.toDomainCredentialType(cmd.getCredentialType());

        // 检查凭证类型转换是否成功
        if (credentialType == null) {
            return SingleResponse.<UserInitializationCO>buildFailure(
                    AuthErrorCode.INVALID_CREDENTIAL_TYPE.getErrCode(),
                    AuthErrorCode.INVALID_CREDENTIAL_TYPE.getErrDesc());
        }

        // 检查凭证是否已存在
        AuthCredential existing =
                authCredentialRepository.findByTypeAndValue(
                        credentialType, cmd.getCredentialValue());
        if (existing != null) {
            return SingleResponse.<UserInitializationCO>buildFailure(
                    AuthErrorCode.CREDENTIAL_ALREADY_EXISTS.getErrCode(),
                    AuthErrorCode.CREDENTIAL_ALREADY_EXISTS.getErrDesc());
        }

        // 判断是否需要密码（OAuth 场景不需要密码）
        boolean needPassword = !credentialType.isThirdParty();

        String plainPassword = null;
        String passwordHash = null;

        // 对于需要密码的场景，处理密码
        if (needPassword) {
            // 如果未提供初始密码，自动生成
            if (cmd.getInitialPassword() == null || cmd.getInitialPassword().isBlank()) {
                plainPassword = passwordGeneratorService.generateDefaultPassword();
            } else {
                plainPassword = cmd.getInitialPassword();
            }
            // 加密密码
            passwordHash = passwordService.encode(plainPassword);
        } else {
            // OAuth 场景必须提供 provider
            if (cmd.getProvider() == null || cmd.getProvider().isBlank()) {
                return SingleResponse.<UserInitializationCO>buildFailure(
                        AuthErrorCode.PROVIDER_REQUIRED.getErrCode(),
                        AuthErrorCode.PROVIDER_REQUIRED.getErrDesc());
            }
        }

        // 创建凭证
        AuthCredential credential;
        if (credentialType.isThirdParty()) {
            // OAuth 类型：使用 createThirdParty 创建
            credential =
                    AuthCredential.createThirdParty(
                            cmd.getUserId(),
                            credentialType,
                            cmd.getCredentialValue(),
                            cmd.getProvider());
        } else {
            // 其他类型：使用 create 创建
            credential =
                    AuthCredential.create(
                            cmd.getUserId(), credentialType, cmd.getCredentialValue());
        }

        // 设置凭证验证状态
        if (cmd.getVerified() != null && cmd.getVerified()) {
            credential.verify();
        }

        // 设置是否为主凭证
        if (Boolean.TRUE.equals(cmd.getIsPrimary())) {
            credential.setAsPrimary();
        }

        // 保存凭证
        authCredentialRepository.save(credential);

        // 对于需要密码的场景，创建并保存密码
        if (needPassword) {
            AuthPassword password =
                    AuthPassword.createInitialPassword(cmd.getUserId(), passwordHash);
            authPasswordRepository.save(password);
        }

        // 组装返回结果
        UserInitializationCO co;
        if (needPassword) {
            co = authAssembler.toUserInitializationCO(credential, plainPassword);
        } else {
            co = authAssembler.toUserInitializationCO(credential);
        }

        return SingleResponse.of(co);
    }
}
