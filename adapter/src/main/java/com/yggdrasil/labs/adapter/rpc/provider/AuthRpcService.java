package com.yggdrasil.labs.adapter.rpc.provider;

import com.alibaba.cola.dto.Response;
import com.yggdrasil.labs.client.dto.cmd.DisableUserCmd;
import com.yggdrasil.labs.client.dto.cmd.EnableUserCmd;
import com.yggdrasil.labs.client.dto.cmd.LockUserCmd;
import com.yggdrasil.labs.client.dto.cmd.UnlockUserCmd;

/**
 * 认证服务 RPC 接口
 *
 * <p>通过 Dubbo RPC 提供账户管理接口，仅用于内部服务间通信
 *
 * <p><b>注意：</b>本接口仅提供账户管理相关的方法（锁定、解锁、禁用、启用），不通过 HTTP API 暴露
 *
 * @author YoungerYang-Y
 */
public interface AuthRpcService {

    /**
     * 锁定账户
     *
     * @param cmd 锁定账户命令
     * @return 操作结果
     */
    Response lockUser(LockUserCmd cmd);

    /**
     * 解锁账户
     *
     * @param cmd 解锁账户命令
     * @return 操作结果
     */
    Response unlockUser(UnlockUserCmd cmd);

    /**
     * 禁用账户
     *
     * @param cmd 禁用账户命令
     * @return 操作结果
     */
    Response disableUser(DisableUserCmd cmd);

    /**
     * 启用账户
     *
     * @param cmd 启用账户命令
     * @return 操作结果
     */
    Response enableUser(EnableUserCmd cmd);
}
