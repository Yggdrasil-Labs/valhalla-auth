package com.yggdrasil.labs.app.auth.executor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.alibaba.cola.dto.SingleResponse;
import com.yggdrasil.labs.app.auth.dto.cmd.VerifyTokenCmd;
import com.yggdrasil.labs.app.auth.dto.co.VerifyTokenCO;
import com.yggdrasil.labs.app.auth.service.TokenService;
import com.yggdrasil.labs.app.auth.service.TokenServiceException;
import com.yggdrasil.labs.app.auth.service.VerifyTokenResult;

/** VerifyTokenExecutor 单元测试 */
@ExtendWith(MockitoExtension.class)
class VerifyTokenExecutorTest {

    @Mock private TokenService tokenService;

    @InjectMocks private VerifyTokenExecutor verifyTokenExecutor;

    @Test
    void execute_withValidToken_shouldReturnVerifyTokenCO() {
        VerifyTokenCmd cmd = new VerifyTokenCmd();
        cmd.setToken("valid-access-token");

        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(15);
        VerifyTokenResult result = new VerifyTokenResult(1001L, expiresAt, false);
        when(tokenService.verifyAccessToken("valid-access-token")).thenReturn(result);

        SingleResponse<VerifyTokenCO> response = verifyTokenExecutor.execute(cmd);

        assertTrue(response.isSuccess());
        assertNotNull(response.getData());
        assertEquals(1001L, response.getData().getUserId());
        assertEquals(expiresAt, response.getData().getExpiresAt());
        assertFalse(response.getData().getDegraded());
    }

    @Test
    void execute_withExpiredToken_shouldReturnTokenExpiredError() {
        VerifyTokenCmd cmd = new VerifyTokenCmd();
        cmd.setToken("expired-token");

        when(tokenService.verifyAccessToken("expired-token"))
                .thenThrow(TokenServiceException.tokenExpired());

        SingleResponse<VerifyTokenCO> response = verifyTokenExecutor.execute(cmd);

        assertFalse(response.isSuccess());
        assertEquals("TOKEN_EXPIRED", response.getErrCode());
        assertEquals("令牌已过期", response.getErrMessage());
    }

    @Test
    void execute_withRevokedToken_shouldReturnTokenRevokedError() {
        VerifyTokenCmd cmd = new VerifyTokenCmd();
        cmd.setToken("revoked-token");

        when(tokenService.verifyAccessToken("revoked-token"))
                .thenThrow(TokenServiceException.tokenRevoked());

        SingleResponse<VerifyTokenCO> response = verifyTokenExecutor.execute(cmd);

        assertFalse(response.isSuccess());
        assertEquals("TOKEN_REVOKED", response.getErrCode());
        assertEquals("令牌已吊销", response.getErrMessage());
    }

    @Test
    void execute_withInvalidToken_shouldReturnTokenInvalidError() {
        VerifyTokenCmd cmd = new VerifyTokenCmd();
        cmd.setToken("invalid-token");

        when(tokenService.verifyAccessToken("invalid-token"))
                .thenThrow(TokenServiceException.tokenInvalid());

        SingleResponse<VerifyTokenCO> response = verifyTokenExecutor.execute(cmd);

        assertFalse(response.isSuccess());
        assertEquals("TOKEN_INVALID", response.getErrCode());
        assertEquals("令牌无效", response.getErrMessage());
    }

    @Test
    void execute_whenRedisDegraded_shouldReturnDegradedTrue() {
        VerifyTokenCmd cmd = new VerifyTokenCmd();
        cmd.setToken("valid-token-degraded");

        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(15);
        VerifyTokenResult result = new VerifyTokenResult(2002L, expiresAt, true);
        when(tokenService.verifyAccessToken("valid-token-degraded")).thenReturn(result);

        SingleResponse<VerifyTokenCO> response = verifyTokenExecutor.execute(cmd);

        assertTrue(response.isSuccess());
        assertNotNull(response.getData());
        assertEquals(2002L, response.getData().getUserId());
        assertEquals(expiresAt, response.getData().getExpiresAt());
        assertTrue(response.getData().getDegraded());
    }
}
