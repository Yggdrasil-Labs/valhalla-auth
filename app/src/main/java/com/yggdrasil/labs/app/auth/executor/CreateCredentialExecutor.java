package com.yggdrasil.labs.app.auth.executor;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.cola.dto.Response;
import com.yggdrasil.labs.app.auth.convert.AuthConverter;
import com.yggdrasil.labs.client.dto.cmd.CreateCredentialCmd;
import com.yggdrasil.labs.client.dto.enums.AuthErrorCode;
import com.yggdrasil.labs.domain.auth.model.AuthCredential;
import com.yggdrasil.labs.domain.auth.model.enums.CredentialType;
import com.yggdrasil.labs.domain.auth.repository.AuthCredentialRepository;

/**
 * 创建凭证用例执行器
 *
 * @author YoungerYang-Y
 */
@Component
public class CreateCredentialExecutor {

    @Resource private AuthCredentialRepository authCredentialRepository;

    /** 执行创建凭证用例 */
    @Transactional(rollbackFor = Exception.class)
    public Response execute(CreateCredentialCmd cmd) {
        CredentialType credentialType =
                AuthConverter.INSTANCE.toDomainCredentialType(cmd.getCredentialType());

        // 检查凭证是否已存在
        AuthCredential existing =
                authCredentialRepository.findByTypeAndValue(
                        credentialType, cmd.getCredentialValue());
        if (existing != null) {
            return Response.buildFailure(
                    AuthErrorCode.CREDENTIAL_ALREADY_EXISTS.getErrCode(),
                    AuthErrorCode.CREDENTIAL_ALREADY_EXISTS.getErrDesc());
        }

        // 创建凭证
        AuthCredential credential;
        // TODO: CreateCredentialCmd 需要添加 provider 字段，暂时使用 thirdPartyName 作为 provider
        if (credentialType == CredentialType.OAUTH && cmd.getThirdPartyName() != null) {
            credential =
                    AuthCredential.createThirdParty(
                            cmd.getUserId(),
                            credentialType,
                            cmd.getCredentialValue(),
                            cmd.getThirdPartyName());
        } else {
            credential =
                    AuthCredential.create(
                            cmd.getUserId(), credentialType, cmd.getCredentialValue());
        }

        if (Boolean.TRUE.equals(cmd.getIsPrimary())) {
            credential.setAsPrimary();
        }

        authCredentialRepository.save(credential);

        return Response.buildSuccess();
    }
}
