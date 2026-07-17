package com.yggdrasil.labs.app.auth.executor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.alibaba.cola.dto.SingleResponse;
import com.yggdrasil.labs.app.auth.dto.cmd.RefreshTokenCmd;
import com.yggdrasil.labs.app.auth.dto.co.TokenCO;
import com.yggdrasil.labs.app.auth.service.RefreshTokenResult;
import com.yggdrasil.labs.app.auth.service.TokenService;
import com.yggdrasil.labs.app.auth.service.TokenServiceException;

/** RefreshTokenExecutor 单元测试 */
@ExtendWith(MockitoExtension.class)
class RefreshTokenExecutorTest {

    @Mock private TokenService tokenService;

    @InjectMocks private RefreshTokenExecutor refreshTokenExecutor;

    private RefreshTokenCmd cmd;

    @BeforeEach
    void setUp() {
        cmd = new RefreshTokenCmd();
        cmd.setRefreshToken("valid-refresh-token");
    }

    @Test
    void execute_withValidRefreshToken_shouldReturnNewAccessToken() {
        when(tokenService.refreshAccessToken("valid-refresh-token"))
                .thenReturn(new RefreshTokenResult("new-access-token", 900L));

        SingleResponse<TokenCO> response = refreshTokenExecutor.execute(cmd);

        assertTrue(response.isSuccess());
        assertNotNull(response.getData());
        assertEquals("new-access-token", response.getData().getAccessToken());
        assertEquals(900L, response.getData().getExpiresIn());
        assertNull(response.getData().getRefreshToken());
    }

    @Test
    void execute_whenTokenExpired_shouldReturnErrorCode() {
        when(tokenService.refreshAccessToken("valid-refresh-token"))
                .thenThrow(TokenServiceException.tokenExpired());

        SingleResponse<TokenCO> response = refreshTokenExecutor.execute(cmd);

        assertFalse(response.isSuccess());
        assertEquals("TOKEN_EXPIRED", response.getErrCode());
        assertEquals("令牌已过期", response.getErrMessage());
    }

    @Test
    void execute_whenTokenRevoked_shouldReturnErrorCode() {
        when(tokenService.refreshAccessToken("valid-refresh-token"))
                .thenThrow(TokenServiceException.tokenRevoked());

        SingleResponse<TokenCO> response = refreshTokenExecutor.execute(cmd);

        assertFalse(response.isSuccess());
        assertEquals("TOKEN_REVOKED", response.getErrCode());
        assertEquals("令牌已吊销", response.getErrMessage());
    }

    @Test
    void execute_whenRedisUnavailable_shouldReturnErrorCode() {
        when(tokenService.refreshAccessToken("valid-refresh-token"))
                .thenThrow(TokenServiceException.redisUnavailable());

        SingleResponse<TokenCO> response = refreshTokenExecutor.execute(cmd);

        assertFalse(response.isSuccess());
        assertEquals("REDIS_UNAVAILABLE", response.getErrCode());
        assertEquals("服务暂时不可用，请稍后重试", response.getErrMessage());
    }
}
