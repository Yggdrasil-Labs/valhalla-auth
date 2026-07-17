package com.yggdrasil.labs.app.auth.executor;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Component;

import com.alibaba.cola.dto.SingleResponse;
import com.yggdrasil.labs.app.auth.dto.cmd.RefreshTokenCmd;
import com.yggdrasil.labs.app.auth.dto.co.TokenCO;
import com.yggdrasil.labs.app.auth.service.RefreshTokenResult;
import com.yggdrasil.labs.app.auth.service.TokenService;
import com.yggdrasil.labs.app.auth.service.TokenServiceException;

/**
 * Token 刷新用例执行器
 *
 * @author YoungerYang-Y
 */
@Component
public class RefreshTokenExecutor {

    @Resource private TokenService tokenService;

    /** 执行 Token 刷新用例 */
    public SingleResponse<TokenCO> execute(RefreshTokenCmd cmd) {
        try {
            RefreshTokenResult result = tokenService.refreshAccessToken(cmd.getRefreshToken());
            TokenCO tokenCO = new TokenCO();
            tokenCO.setAccessToken(result.getAccessToken());
            tokenCO.setExpiresIn(result.getExpiresIn());
            // refreshToken 不返回，原 RT 保持有效
            return SingleResponse.of(tokenCO);
        } catch (TokenServiceException e) {
            return SingleResponse.buildFailure(e.getErrorCode(), e.getMessage());
        }
    }
}
