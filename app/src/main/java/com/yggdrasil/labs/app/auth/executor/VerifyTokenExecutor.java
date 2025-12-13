package com.yggdrasil.labs.app.auth.executor;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Component;

import com.alibaba.cola.dto.SingleResponse;
import com.yggdrasil.labs.app.auth.assembler.AuthAssembler;
import com.yggdrasil.labs.client.dto.cmd.VerifyTokenCmd;
import com.yggdrasil.labs.client.dto.co.AuthUserCO;
import com.yggdrasil.labs.client.dto.enums.AuthErrorCode;
import com.yggdrasil.labs.domain.auth.repository.AuthTokenRepository;
import com.yggdrasil.labs.domain.auth.repository.AuthUserRepository;

/**
 * Token 验证用例执行器
 *
 * @author YoungerYang-Y
 */
@Component
public class VerifyTokenExecutor {

    @Resource private AuthTokenRepository authTokenRepository;
    @Resource private AuthUserRepository authUserRepository;
    @Resource private AuthAssembler authAssembler;

    /** 执行 Token 验证用例 */
    public SingleResponse<AuthUserCO> execute(VerifyTokenCmd cmd) {
        // TODO: 实现 Token 验证逻辑
        // 1. 解析 JWT Token
        // 2. 验证 Token 签名和过期时间
        // 3. 查找 Token 记录并验证状态
        // 4. 查找用户信息并返回
        return SingleResponse.buildFailure(
                AuthErrorCode.VERIFY_TOKEN_NOT_IMPLEMENTED.getErrCode(),
                AuthErrorCode.VERIFY_TOKEN_NOT_IMPLEMENTED.getErrDesc());
    }
}
