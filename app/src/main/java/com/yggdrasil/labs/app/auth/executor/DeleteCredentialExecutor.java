package com.yggdrasil.labs.app.auth.executor;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.cola.dto.Response;
import com.yggdrasil.labs.client.dto.cmd.DeleteCredentialCmd;
import com.yggdrasil.labs.client.dto.enums.AuthErrorCode;
import com.yggdrasil.labs.domain.auth.model.AuthCredential;
import com.yggdrasil.labs.domain.auth.repository.AuthCredentialRepository;

/**
 * 删除凭证用例执行器
 *
 * @author YoungerYang-Y
 */
@Component
public class DeleteCredentialExecutor {

    @Resource private AuthCredentialRepository authCredentialRepository;

    /** 执行删除凭证用例 */
    @Transactional(rollbackFor = Exception.class)
    public Response execute(DeleteCredentialCmd cmd) {
        AuthCredential credential =
                authCredentialRepository.findByCredentialId(cmd.getCredentialId());
        if (credential == null) {
            return Response.buildFailure(
                    AuthErrorCode.CREDENTIAL_NOT_FOUND.getErrCode(),
                    AuthErrorCode.CREDENTIAL_NOT_FOUND.getErrDesc());
        }

        // 检查是否为主凭证
        if (Boolean.TRUE.equals(credential.getIsPrimary())) {
            return Response.buildFailure(
                    AuthErrorCode.CANNOT_DELETE_PRIMARY_CREDENTIAL.getErrCode(),
                    AuthErrorCode.CANNOT_DELETE_PRIMARY_CREDENTIAL.getErrDesc());
        }

        authCredentialRepository.delete(cmd.getCredentialId());

        return Response.buildSuccess();
    }
}
