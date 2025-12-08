package com.yggdrasil.labs.client.api;

import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import com.yggdrasil.labs.client.dto.cmd.CreateCredentialCmd;
import com.yggdrasil.labs.client.dto.cmd.DeleteCredentialCmd;
import com.yggdrasil.labs.client.dto.cmd.DisableUserCmd;
import com.yggdrasil.labs.client.dto.cmd.EnableUserCmd;
import com.yggdrasil.labs.client.dto.cmd.LockUserCmd;
import com.yggdrasil.labs.client.dto.cmd.LoginCmd;
import com.yggdrasil.labs.client.dto.cmd.LogoutCmd;
import com.yggdrasil.labs.client.dto.cmd.RefreshTokenCmd;
import com.yggdrasil.labs.client.dto.cmd.UnlockUserCmd;
import com.yggdrasil.labs.client.dto.cmd.VerifyTokenCmd;
import com.yggdrasil.labs.client.dto.co.AuthUserCO;
import com.yggdrasil.labs.client.dto.co.CredentialCO;
import com.yggdrasil.labs.client.dto.co.LoginResultCO;
import com.yggdrasil.labs.client.dto.co.TokenCO;
import com.yggdrasil.labs.client.dto.query.GetTokenQuery;
import com.yggdrasil.labs.client.dto.query.GetUserQuery;
import com.yggdrasil.labs.client.dto.query.ListCredentialsQuery;

/**
 * 认证服务客户端接口
 *
 * <p>定义认证服务的所有对外业务接口
 *
 * @author YoungerYang-Y
 */
public interface AuthClient {

    /**
     * 用户登录
     *
     * @param cmd 登录命令
     * @return 登录结果（包含 Token 和用户信息）
     */
    SingleResponse<LoginResultCO> login(LoginCmd cmd);

    /**
     * 刷新 Token
     *
     * @param cmd Token 刷新命令
     * @return Token 信息
     */
    SingleResponse<TokenCO> refreshToken(RefreshTokenCmd cmd);

    /**
     * 用户登出
     *
     * @param cmd 登出命令
     * @return 操作结果
     */
    Response logout(LogoutCmd cmd);

    /**
     * 验证 Token
     *
     * @param cmd Token 验证命令
     * @return 验证结果（包含用户ID等信息）
     */
    SingleResponse<AuthUserCO> verifyToken(VerifyTokenCmd cmd);

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

    /**
     * 创建凭证
     *
     * @param cmd 创建凭证命令
     * @return 操作结果
     */
    Response createCredential(CreateCredentialCmd cmd);

    /**
     * 删除凭证
     *
     * @param cmd 删除凭证命令
     * @return 操作结果
     */
    Response deleteCredential(DeleteCredentialCmd cmd);

    /**
     * 查询用户信息
     *
     * @param query 查询用户信息
     * @return 用户认证信息
     */
    SingleResponse<AuthUserCO> getUser(GetUserQuery query);

    /**
     * 查询凭证列表
     *
     * @param query 查询凭证列表
     * @return 凭证列表
     */
    MultiResponse<CredentialCO> listCredentials(ListCredentialsQuery query);

    /**
     * 查询 Token 信息
     *
     * @param query 查询 Token 信息
     * @return Token 信息
     */
    SingleResponse<TokenCO> getToken(GetTokenQuery query);
}
