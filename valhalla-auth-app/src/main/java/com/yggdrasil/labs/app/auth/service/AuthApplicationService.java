package com.yggdrasil.labs.app.auth.service;

import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import com.yggdrasil.labs.app.auth.dto.cmd.CreateCredentialCmd;
import com.yggdrasil.labs.app.auth.dto.cmd.DeleteCredentialCmd;
import com.yggdrasil.labs.app.auth.dto.cmd.InitializeUserCmd;
import com.yggdrasil.labs.app.auth.dto.cmd.LoginCmd;
import com.yggdrasil.labs.app.auth.dto.cmd.LogoutCmd;
import com.yggdrasil.labs.app.auth.dto.cmd.RefreshTokenCmd;
import com.yggdrasil.labs.app.auth.dto.cmd.VerifyTokenCmd;
import com.yggdrasil.labs.app.auth.dto.co.AuthUserCO;
import com.yggdrasil.labs.app.auth.dto.co.CredentialCO;
import com.yggdrasil.labs.app.auth.dto.co.LoginResultCO;
import com.yggdrasil.labs.app.auth.dto.co.TokenCO;
import com.yggdrasil.labs.app.auth.dto.co.UserInitializationCO;
import com.yggdrasil.labs.app.auth.dto.co.VerifyTokenCO;
import com.yggdrasil.labs.app.auth.dto.query.GetTokenQuery;
import com.yggdrasil.labs.app.auth.dto.query.GetUserQuery;
import com.yggdrasil.labs.app.auth.dto.query.ListCredentialsQuery;

/**
 * 认证应用服务接口
 *
 * <p>定义认证服务的所有业务操作，供 Adapter 层直接调用
 *
 * @author YoungerYang-Y
 */
public interface AuthApplicationService {

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
    SingleResponse<VerifyTokenCO> verifyToken(VerifyTokenCmd cmd);

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

    /**
     * 初始化用户
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
    SingleResponse<UserInitializationCO> initializeUser(InitializeUserCmd cmd);
}
