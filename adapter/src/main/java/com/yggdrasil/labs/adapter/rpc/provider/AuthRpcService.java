package com.yggdrasil.labs.adapter.rpc.provider;

import com.alibaba.cola.dto.Response;
import com.yggdrasil.labs.client.dto.cmd.CreateCredentialCmd;

/**
 * 认证服务 RPC 接口
 *
 * <p>通过 Dubbo RPC 提供认证服务接口，仅用于内部服务间通信
 *
 * <p><b>注意：</b>本接口仅提供用户管理服务所需的认证相关方法（同步创建凭证），不通过 HTTP API 暴露
 *
 * @author YoungerYang-Y
 */
public interface AuthRpcService {

    /**
     * 为用户创建认证凭证（同步创建用户时调用）
     *
     * <p>当 User 管理服务创建用户成功后，应调用本方法在认证模块中同步创建凭证信息， 保证认证模块与用户管理模块的数据一致性。
     *
     * @param cmd 创建凭证命令
     * @return 操作结果
     */
    Response createCredential(CreateCredentialCmd cmd);
}
