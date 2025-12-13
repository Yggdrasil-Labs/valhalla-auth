package com.yggdrasil.labs.app.auth.executor;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.cola.dto.Response;
import com.yggdrasil.labs.client.dto.cmd.EnableUserCmd;
import com.yggdrasil.labs.client.dto.enums.AuthErrorCode;
import com.yggdrasil.labs.domain.auth.model.AuthUser;
import com.yggdrasil.labs.domain.auth.repository.AuthUserRepository;

/**
 * 启用账户用例执行器
 *
 * @author YoungerYang-Y
 */
@Component
public class EnableUserExecutor {

    @Resource private AuthUserRepository authUserRepository;

    /** 执行启用账户用例 */
    @Transactional(rollbackFor = Exception.class)
    public Response execute(EnableUserCmd cmd) {
        AuthUser authUser = authUserRepository.findByUserId(cmd.getUserId());
        if (authUser == null) {
            return Response.buildFailure(
                    AuthErrorCode.USER_NOT_FOUND.getErrCode(),
                    AuthErrorCode.USER_NOT_FOUND.getErrDesc());
        }

        authUser.enable();
        authUserRepository.update(authUser);

        return Response.buildSuccess();
    }
}
