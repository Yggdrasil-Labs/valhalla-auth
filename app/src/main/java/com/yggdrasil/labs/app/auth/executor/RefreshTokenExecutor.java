package com.yggdrasil.labs.app.auth.executor;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.cola.dto.SingleResponse;
import com.yggdrasil.labs.client.dto.cmd.RefreshTokenCmd;
import com.yggdrasil.labs.client.dto.co.TokenCO;
import com.yggdrasil.labs.client.dto.enums.AuthErrorCode;
import com.yggdrasil.labs.domain.auth.repository.AuthTokenRepository;

/**
 * Token 刷新用例执行器
 *
 * @author YoungerYang-Y
 */
@Component
public class RefreshTokenExecutor {

    @Resource private AuthTokenRepository authTokenRepository;

    /** 执行 Token 刷新用例 */
    @Transactional(rollbackFor = Exception.class)
    public SingleResponse<TokenCO> execute(RefreshTokenCmd cmd) {
        // TODO: 实现 Token 刷新逻辑
        // 1. 验证 Refresh Token
        // 2. 生成新的 Access Token
        // 3. 更新 Token 记录
        return SingleResponse.buildFailure(
                AuthErrorCode.REFRESH_TOKEN_NOT_IMPLEMENTED.getErrCode(),
                AuthErrorCode.REFRESH_TOKEN_NOT_IMPLEMENTED.getErrDesc());
    }
}
