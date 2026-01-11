package com.yggdrasil.labs.adapter.rpc.provider;

import jakarta.annotation.Resource;

import org.apache.dubbo.config.annotation.DubboService;

import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import com.yggdrasil.labs.adapter.rpc.convert.AuthRpcConverter;
import com.yggdrasil.labs.app.auth.dto.co.UserInitializationCO;
import com.yggdrasil.labs.app.auth.service.AuthApplicationService;
import com.yggdrasil.labs.client.api.AuthRpcFacade;
import com.yggdrasil.labs.client.dto.cmd.RpcCreateCredentialCmd;
import com.yggdrasil.labs.client.dto.cmd.RpcInitializeUserCmd;
import com.yggdrasil.labs.client.dto.co.RpcUserInitializationCO;

/**
 * 认证服务 RPC Facade 实现
 *
 * <p>通过 Dubbo RPC 提供认证服务接口的实现，调用 App 层 ApplicationService
 *
 * <p><b>注意：</b>本服务仅提供用户管理服务所需的认证相关方法，不通过 HTTP API 暴露
 *
 * @author YoungerYang-Y
 */
@DubboService(version = "1.0.0", group = "auth", interfaceClass = AuthRpcFacade.class)
public class AuthRpcFacadeImpl implements AuthRpcFacade {

    private static final AuthRpcConverter CONVERTER = AuthRpcConverter.INSTANCE;

    @Resource private AuthApplicationService authApplicationService;

    @Override
    public Response createCredential(RpcCreateCredentialCmd cmd) {
        var appCmd = CONVERTER.toAppCmd(cmd);
        return authApplicationService.createCredential(appCmd);
    }

    @Override
    public SingleResponse<RpcUserInitializationCO> initializeUser(RpcInitializeUserCmd cmd) {
        var appCmd = CONVERTER.toAppCmd(cmd);
        SingleResponse<UserInitializationCO> appResponse =
                authApplicationService.initializeUser(appCmd);

        if (appResponse.isSuccess() && appResponse.getData() != null) {
            RpcUserInitializationCO rpcCO = CONVERTER.toRpcCO(appResponse.getData());
            return SingleResponse.of(rpcCO);
        }

        return SingleResponse.buildFailure(appResponse.getErrCode(), appResponse.getErrMessage());
    }
}
