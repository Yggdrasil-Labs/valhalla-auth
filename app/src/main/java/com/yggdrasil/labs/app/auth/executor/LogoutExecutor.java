package com.yggdrasil.labs.app.auth.executor;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.cola.dto.Response;
import com.yggdrasil.labs.client.dto.cmd.LogoutCmd;
import com.yggdrasil.labs.domain.auth.repository.AuthTokenRepository;

/**
 * 登出用例执行器
 *
 * @author YoungerYang-Y
 */
@Component
public class LogoutExecutor {

    @Resource private AuthTokenRepository authTokenRepository;

    /** 执行登出用例 */
    @Transactional(rollbackFor = Exception.class)
    public Response execute(LogoutCmd cmd) {
        // TODO: 实现登出逻辑
        // 1. 撤销 Token（根据 accessToken 或 deviceId）
        // 2. 如果 revokeAll=true，撤销所有 Token
        // 3. 更新 Token 记录状态
        return Response.buildSuccess();
    }
}
