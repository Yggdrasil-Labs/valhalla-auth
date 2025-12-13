package com.yggdrasil.labs.app.auth.executor;

import java.time.LocalDateTime;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.cola.dto.Response;
import com.yggdrasil.labs.client.dto.cmd.LockUserCmd;
import com.yggdrasil.labs.client.dto.enums.AuthErrorCode;
import com.yggdrasil.labs.domain.auth.model.AuthUser;
import com.yggdrasil.labs.domain.auth.repository.AuthUserRepository;

/**
 * 锁定账户用例执行器
 *
 * @author YoungerYang-Y
 */
@Component
public class LockUserExecutor {

    @Resource private AuthUserRepository authUserRepository;

    /** 执行锁定账户用例 */
    @Transactional(rollbackFor = Exception.class)
    public Response execute(LockUserCmd cmd) {
        AuthUser authUser = authUserRepository.findByUserId(cmd.getUserId());
        if (authUser == null) {
            return Response.buildFailure(
                    AuthErrorCode.USER_NOT_FOUND.getErrCode(),
                    AuthErrorCode.USER_NOT_FOUND.getErrDesc());
        }

        LocalDateTime lockedUntil = LocalDateTime.now().plusMinutes(cmd.getLockDurationMinutes());
        authUser.lock(lockedUntil);
        authUserRepository.update(authUser);

        return Response.buildSuccess();
    }
}
