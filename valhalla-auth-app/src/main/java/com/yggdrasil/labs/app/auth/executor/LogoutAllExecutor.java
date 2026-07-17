package com.yggdrasil.labs.app.auth.executor;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Component;

import com.alibaba.cola.dto.Response;
import com.yggdrasil.labs.app.auth.dto.cmd.LogoutCmd;
import com.yggdrasil.labs.app.auth.service.TokenService;
import com.yggdrasil.labs.app.auth.service.TokenServiceException;

/**
 * 全部登出用例执行器（吊销用户所有会话令牌）
 *
 * @author YoungerYang-Y
 */
@Component
public class LogoutAllExecutor {

    @Resource private TokenService tokenService;

    /** 执行全部登出用例 */
    public Response execute(LogoutCmd cmd) {
        try {
            tokenService.revokeAllTokens(cmd.getUserId());
            return Response.buildSuccess();
        } catch (TokenServiceException e) {
            return Response.buildFailure(e.getErrorCode(), e.getMessage());
        }
    }
}
