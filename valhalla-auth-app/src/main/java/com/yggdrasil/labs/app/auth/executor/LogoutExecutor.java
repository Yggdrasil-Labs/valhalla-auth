package com.yggdrasil.labs.app.auth.executor;

import org.springframework.stereotype.Component;

import com.alibaba.cola.dto.Response;
import com.yggdrasil.labs.app.auth.dto.cmd.LogoutCmd;

/**
 * 登出用例执行器
 *
 * @author YoungerYang-Y
 */
@Component
public class LogoutExecutor {

    /** 执行登出用例 */
    public Response execute(LogoutCmd cmd) {
        // TODO: 实现登出逻辑
        // 1. 撤销 Token（根据 accessToken 或 deviceId）- 仅操作 Redis
        // 2. 如果 revokeAll=true，撤销所有 Token - 仅操作 Redis
        // 注意：Token 信息存储在 Redis 中，不操作数据库
        return Response.buildSuccess();
    }
}
