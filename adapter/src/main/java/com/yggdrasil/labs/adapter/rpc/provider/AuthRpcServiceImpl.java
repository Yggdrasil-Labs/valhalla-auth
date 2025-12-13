package com.yggdrasil.labs.adapter.rpc.provider;

import jakarta.annotation.Resource;

import org.apache.dubbo.config.annotation.DubboService;

import com.alibaba.cola.dto.Response;
import com.yggdrasil.labs.client.api.AuthClient;
import com.yggdrasil.labs.client.dto.cmd.DisableUserCmd;
import com.yggdrasil.labs.client.dto.cmd.EnableUserCmd;
import com.yggdrasil.labs.client.dto.cmd.LockUserCmd;
import com.yggdrasil.labs.client.dto.cmd.UnlockUserCmd;

/**
 * 认证服务 RPC 实现
 *
 * <p>通过 Dubbo RPC 提供账户管理接口的实现，调用 Client 层接口
 *
 * <p><b>注意：</b>本服务仅提供账户管理相关的方法，不通过 HTTP API 暴露
 *
 * @author YoungerYang-Y
 */
@DubboService(version = "1.0.0", group = "auth", interfaceClass = AuthRpcService.class)
public class AuthRpcServiceImpl implements AuthRpcService {

    @Resource private AuthClient authClient;

    @Override
    public Response lockUser(LockUserCmd cmd) {
        return authClient.lockUser(cmd);
    }

    @Override
    public Response unlockUser(UnlockUserCmd cmd) {
        return authClient.unlockUser(cmd);
    }

    @Override
    public Response disableUser(DisableUserCmd cmd) {
        return authClient.disableUser(cmd);
    }

    @Override
    public Response enableUser(EnableUserCmd cmd) {
        return authClient.enableUser(cmd);
    }
}
