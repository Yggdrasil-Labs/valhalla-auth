package com.yggdrasil.labs.client.api;

import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import com.yggdrasil.labs.client.dto.cmd.RpcCreateCredentialCmd;
import com.yggdrasil.labs.client.dto.cmd.RpcInitializeUserCmd;
import com.yggdrasil.labs.client.dto.cmd.RpcVerifyTokenCmd;
import com.yggdrasil.labs.client.dto.co.RpcUserInitializationCO;
import com.yggdrasil.labs.client.dto.co.RpcVerifyTokenCO;

/**
 * 认证服务 Dubbo RPC Facade 接口
 *
 * <p>定义认证服务的 RPC 对外契约接口，仅用于内部服务间通信
 *
 * <p><b>注意：</b>本接口仅提供用户管理服务所需的认证相关方法，不通过 HTTP API 暴露
 *
 * @author YoungerYang-Y
 */
public interface AuthRpcFacade {

    /**
     * 为用户创建认证凭证（同步创建用户时调用）
     *
     * <p>当 User 管理服务创建用户成功后，应调用本方法在认证模块中同步创建凭证信息， 保证认证模块与用户管理模块的数据一致性。
     *
     * @param cmd 创建凭证命令
     * @return 操作结果
     */
    Response createCredential(RpcCreateCredentialCmd cmd);

    /**
     * 初始化用户（创建用户时调用）
     *
     * <p>用于初始化用户认证凭证和初始密码（如适用），支持多种用户创建场景：
     *
     * <ul>
     *   <li>管理员创建：USERNAME + 密码
     *   <li>用户密码注册：USERNAME/PHONE/EMAIL + 密码
     *   <li>电话短信注册：PHONE + 验证状态 + 密码（可选）
     *   <li>邮箱注册：EMAIL + 验证状态 + 密码（可选）
     *   <li>第三方 OAuth 登录：WECHAT/GOOGLE/OTHER + provider（不需要密码）
     * </ul>
     *
     * @param cmd 用户初始化命令
     * @return 用户初始化结果（包含凭证信息和初始密码，如适用）
     */
    SingleResponse<RpcUserInitializationCO> initializeUser(RpcInitializeUserCmd cmd);

    /**
     * 验证 Token
     *
     * <p>供外部服务通过 Dubbo RPC 验证 Token 有效性，返回用户ID、过期时间等信息
     *
     * @param cmd Token 验证命令
     * @return 验证结果（包含用户ID、过期时间、降级标志）
     */
    SingleResponse<RpcVerifyTokenCO> verifyToken(RpcVerifyTokenCmd cmd);
}
