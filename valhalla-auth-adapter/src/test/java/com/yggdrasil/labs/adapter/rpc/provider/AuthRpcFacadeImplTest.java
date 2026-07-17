package com.yggdrasil.labs.adapter.rpc.provider;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.alibaba.cola.dto.SingleResponse;
import com.yggdrasil.labs.adapter.rpc.convert.AuthRpcConverter;
import com.yggdrasil.labs.app.auth.dto.cmd.VerifyTokenCmd;
import com.yggdrasil.labs.app.auth.dto.co.VerifyTokenCO;
import com.yggdrasil.labs.app.auth.service.AuthApplicationService;
import com.yggdrasil.labs.client.dto.cmd.RpcVerifyTokenCmd;
import com.yggdrasil.labs.client.dto.co.RpcVerifyTokenCO;

/**
 * AuthRpcFacadeImpl 单元测试
 *
 * @author YoungerYang-Y
 */
@ExtendWith(MockitoExtension.class)
class AuthRpcFacadeImplTest {

    @InjectMocks private AuthRpcFacadeImpl authRpcFacadeImpl;

    @Mock private AuthApplicationService authApplicationService;

    @Mock private AuthRpcConverter authRpcConverter;

    private RpcVerifyTokenCmd rpcCmd;

    @BeforeEach
    void setUp() {
        rpcCmd = new RpcVerifyTokenCmd();
        rpcCmd.setToken("test-jwt-token");
    }

    @Test
    @DisplayName("verifyToken - 成功场景：返回 userId、expiresAt、degraded")
    void verifyToken_success() {
        // Arrange
        VerifyTokenCmd appCmd = new VerifyTokenCmd();
        appCmd.setToken("test-jwt-token");

        VerifyTokenCO co = new VerifyTokenCO();
        co.setUserId(12345L);
        co.setExpiresAt(LocalDateTime.of(2026, 7, 18, 12, 0, 0));
        co.setDegraded(false);

        RpcVerifyTokenCO rpcCO = new RpcVerifyTokenCO();
        rpcCO.setUserId(12345L);
        rpcCO.setExpiresAt(LocalDateTime.of(2026, 7, 18, 12, 0, 0));
        rpcCO.setDegraded(false);

        when(authRpcConverter.toAppCmd(rpcCmd)).thenReturn(appCmd);
        when(authApplicationService.verifyToken(appCmd)).thenReturn(SingleResponse.of(co));
        when(authRpcConverter.toRpcCO(co)).thenReturn(rpcCO);

        // Act
        SingleResponse<RpcVerifyTokenCO> result = authRpcFacadeImpl.verifyToken(rpcCmd);

        // Assert
        assertTrue(result.isSuccess());
        assertNotNull(result.getData());
        assertEquals(12345L, result.getData().getUserId());
        assertEquals(LocalDateTime.of(2026, 7, 18, 12, 0, 0), result.getData().getExpiresAt());
        assertFalse(result.getData().getDegraded());

        verify(authRpcConverter).toAppCmd(rpcCmd);
        verify(authApplicationService).verifyToken(appCmd);
        verify(authRpcConverter).toRpcCO(co);
    }

    @Test
    @DisplayName("verifyToken - 失败场景：TOKEN_EXPIRED 错误码传透")
    void verifyToken_tokenExpired_returnsFailure() {
        // Arrange
        VerifyTokenCmd appCmd = new VerifyTokenCmd();
        appCmd.setToken("test-jwt-token");

        when(authRpcConverter.toAppCmd(rpcCmd)).thenReturn(appCmd);
        when(authApplicationService.verifyToken(appCmd))
                .thenReturn(SingleResponse.buildFailure("TOKEN_EXPIRED", "Token已过期"));

        // Act
        SingleResponse<RpcVerifyTokenCO> result = authRpcFacadeImpl.verifyToken(rpcCmd);

        // Assert
        assertFalse(result.isSuccess());
        assertNull(result.getData());
        assertEquals("TOKEN_EXPIRED", result.getErrCode());
        assertEquals("Token已过期", result.getErrMessage());

        verify(authRpcConverter).toAppCmd(rpcCmd);
        verify(authApplicationService).verifyToken(appCmd);
    }
}
