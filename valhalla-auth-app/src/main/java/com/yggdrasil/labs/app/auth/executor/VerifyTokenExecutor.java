package com.yggdrasil.labs.app.auth.executor;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Component;

import com.alibaba.cola.dto.SingleResponse;
import com.yggdrasil.labs.app.auth.dto.cmd.VerifyTokenCmd;
import com.yggdrasil.labs.app.auth.dto.co.VerifyTokenCO;
import com.yggdrasil.labs.app.auth.service.TokenService;
import com.yggdrasil.labs.app.auth.service.TokenServiceException;
import com.yggdrasil.labs.app.auth.service.VerifyTokenResult;

/**
 * Token 验证用例执行器
 *
 * @author YoungerYang-Y
 */
@Component
public class VerifyTokenExecutor {

    @Resource private TokenService tokenService;

    /** 执行 Token 验证用例 */
    public SingleResponse<VerifyTokenCO> execute(VerifyTokenCmd cmd) {
        try {
            VerifyTokenResult result = tokenService.verifyAccessToken(cmd.getToken());
            VerifyTokenCO co = new VerifyTokenCO();
            co.setUserId(result.getUserId());
            co.setExpiresAt(result.getExpiresAt());
            co.setDegraded(result.getDegraded());
            return SingleResponse.of(co);
        } catch (TokenServiceException e) {
            return SingleResponse.buildFailure(e.getErrorCode(), e.getMessage());
        }
    }
}
