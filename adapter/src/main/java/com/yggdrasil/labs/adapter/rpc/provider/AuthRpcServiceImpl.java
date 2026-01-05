package com.yggdrasil.labs.adapter.rpc.provider;

import jakarta.annotation.Resource;

import org.apache.dubbo.config.annotation.DubboService;

import com.alibaba.cola.dto.Response;
import com.yggdrasil.labs.client.api.AuthClient;
import com.yggdrasil.labs.client.dto.cmd.CreateCredentialCmd;

/**
 * 认证服务 RPC 实现
 *
 * <p>通过 Dubbo RPC 提供认证服务接口的实现，调用 Client 层接口
 *
 * <p><b>注意：</b>本服务仅提供用户管理服务所需的认证相关方法，不通过 HTTP API 暴露
 *
 * @author YoungerYang-Y
 */
@DubboService(version = "1.0.0", group = "auth", interfaceClass = AuthRpcService.class)
public class AuthRpcServiceImpl implements AuthRpcService {

    @Resource private AuthClient authClient;

    @Override
    public Response createCredential(CreateCredentialCmd cmd) {
        return authClient.createCredential(cmd);
    }
}
