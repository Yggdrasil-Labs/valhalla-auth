package com.yggdrasil.labs.app.auth.executor;

import java.time.Duration;

import jakarta.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.cola.dto.SingleResponse;
import com.yggdrasil.labs.app.auth.convert.AuthConverter;
import com.yggdrasil.labs.app.auth.dto.cmd.LoginCmd;
import com.yggdrasil.labs.app.auth.dto.co.AuthUserCO;
import com.yggdrasil.labs.app.auth.dto.co.LoginResultCO;
import com.yggdrasil.labs.app.auth.dto.co.TokenCO;
import com.yggdrasil.labs.app.auth.dto.enums.AuthErrorCode;
import com.yggdrasil.labs.app.auth.service.PasswordService;
import com.yggdrasil.labs.app.auth.service.TokenPairResult;
import com.yggdrasil.labs.app.auth.service.TokenService;
import com.yggdrasil.labs.app.auth.service.TokenServiceException;
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
    @Resource private AuthConverter authConverter;
    @Resource private PasswordService passwordService;
    @Resource private TokenService tokenService;

    @Value("${auth.password.lock-threshold:5}")
    private int lockThreshold;

    @Value("${auth.password.lock-duration:30m}")
    private Duration lockDuration;

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
        Long userId = credential.getUserId();
        AuthPassword authPassword = authPasswordRepository.findByUserId(userId);
        if (authPassword == null) {
            return SingleResponse.buildFailure(
                    AuthErrorCode.CREDENTIAL_NOT_FOUND.getErrCode(),
                    AuthErrorCode.CREDENTIAL_NOT_FOUND.getErrDesc());
        }

        // 3. 检查密码状态
        if (!authPassword.isValid()) {
            return SingleResponse.buildFailure(
                    AuthErrorCode.ACCOUNT_DISABLED.getErrCode(),
                    AuthErrorCode.ACCOUNT_DISABLED.getErrDesc());
        }

        // 4. 检查锁定状态
        if (authPassword.isLocked()) {
            return SingleResponse.buildFailure(
                    AuthErrorCode.ACCOUNT_LOCKED.getErrCode(),
                    AuthErrorCode.ACCOUNT_LOCKED.getErrDesc());
        }

        // 5. 验证密码
        if (!passwordService.matches(cmd.getPassword(), authPassword.getPasswordHash())) {
            // 密码不匹配
            authPassword.incrementFailedAttempts();
            if (authPassword.getFailedAttempts() >= lockThreshold) {
                authPassword.lock(lockDuration);
            }
            authPasswordRepository.update(authPassword);

            if (authPassword.getFailedAttempts() >= lockThreshold) {
                return SingleResponse.buildFailure(
                        AuthErrorCode.ACCOUNT_LOCKED.getErrCode(),
                        AuthErrorCode.ACCOUNT_LOCKED.getErrDesc());
            }
            return SingleResponse.buildFailure(
                    AuthErrorCode.CREDENTIAL_NOT_FOUND.getErrCode(),
                    AuthErrorCode.CREDENTIAL_NOT_FOUND.getErrDesc());
        }

        // 6. 密码匹配，重置失败次数
        authPassword.resetFailedAttempts();
        authPasswordRepository.update(authPassword);

        // 7. 签发 Token
        TokenPairResult tokenPairResult;
        try {
            tokenPairResult = tokenService.issueTokenPair(userId);
        } catch (TokenServiceException e) {
            return SingleResponse.buildFailure(
                    AuthErrorCode.REDIS_UNAVAILABLE.getErrCode(),
                    AuthErrorCode.REDIS_UNAVAILABLE.getErrDesc());
        }

        // 8. 组装返回结果
        TokenCO tokenCO = new TokenCO();
        tokenCO.setAccessToken(tokenPairResult.getAccessToken());
        tokenCO.setRefreshToken(tokenPairResult.getRefreshToken());
        tokenCO.setExpiresIn(tokenPairResult.getExpiresIn());

        AuthUserCO authUserCO = new AuthUserCO();
        authUserCO.setUserId(userId);

        LoginResultCO result = new LoginResultCO();
        result.setToken(tokenCO);
        result.setUser(authUserCO);

        return SingleResponse.of(result);
    }
}
