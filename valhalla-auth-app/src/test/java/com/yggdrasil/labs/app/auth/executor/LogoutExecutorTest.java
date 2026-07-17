package com.yggdrasil.labs.app.auth.executor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.alibaba.cola.dto.Response;
import com.yggdrasil.labs.app.auth.dto.cmd.LogoutCmd;
import com.yggdrasil.labs.app.auth.dto.enums.AuthErrorCode;
import com.yggdrasil.labs.app.auth.service.TokenService;
import com.yggdrasil.labs.app.auth.service.TokenServiceException;

/** LogoutExecutor 单元测试 */
@ExtendWith(MockitoExtension.class)
class LogoutExecutorTest {

    @Mock private TokenService tokenService;

    @InjectMocks private LogoutExecutor logoutExecutor;

    private LogoutCmd logoutCmd;

    @BeforeEach
    void setUp() {
        logoutCmd = new LogoutCmd();
        logoutCmd.setUserId(1001L);
        logoutCmd.setAccessToken("test-access-token");
    }

    @Test
    void execute_shouldRevokeTokenSuccessfully() {
        doNothing().when(tokenService).revokeToken("test-access-token");

        Response response = logoutExecutor.execute(logoutCmd);

        assertTrue(response.isSuccess());
        verify(tokenService).revokeToken("test-access-token");
    }

    @Test
    void execute_whenRedisUnavailable_shouldReturnRedisUnavailable() {
        doThrow(TokenServiceException.redisUnavailable())
                .when(tokenService)
                .revokeToken("test-access-token");

        Response response = logoutExecutor.execute(logoutCmd);

        assertFalse(response.isSuccess());
        assertEquals(AuthErrorCode.REDIS_UNAVAILABLE.getErrCode(), response.getErrCode());
    }
}
