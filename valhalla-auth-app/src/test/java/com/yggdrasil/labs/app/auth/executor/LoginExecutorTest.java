package com.yggdrasil.labs.app.auth.executor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.Duration;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.alibaba.cola.dto.SingleResponse;
import com.yggdrasil.labs.app.auth.convert.AuthConverter;
import com.yggdrasil.labs.app.auth.dto.cmd.LoginCmd;
import com.yggdrasil.labs.app.auth.dto.co.LoginResultCO;
import com.yggdrasil.labs.app.auth.dto.enums.AuthErrorCode;
import com.yggdrasil.labs.app.auth.dto.enums.CredentialTypeEnum;
import com.yggdrasil.labs.app.auth.service.PasswordService;
import com.yggdrasil.labs.app.auth.service.TokenPairResult;
import com.yggdrasil.labs.app.auth.service.TokenService;
import com.yggdrasil.labs.app.auth.service.TokenServiceException;
import com.yggdrasil.labs.domain.auth.model.AuthCredential;
import com.yggdrasil.labs.domain.auth.model.AuthPassword;
import com.yggdrasil.labs.domain.auth.model.enums.CredentialType;
import com.yggdrasil.labs.domain.auth.model.enums.PasswordStatus;
import com.yggdrasil.labs.domain.auth.repository.AuthCredentialRepository;
import com.yggdrasil.labs.domain.auth.repository.AuthPasswordRepository;

/** LoginExecutor 单元测试 */
@ExtendWith(MockitoExtension.class)
class LoginExecutorTest {

    @Mock private AuthCredentialRepository authCredentialRepository;
    @Mock private AuthPasswordRepository authPasswordRepository;
    @Mock private AuthConverter authConverter;
    @Mock private PasswordService passwordService;
    @Mock private TokenService tokenService;

    @InjectMocks private LoginExecutor loginExecutor;

    private LoginCmd loginCmd;
    private AuthCredential credential;
    private AuthPassword authPassword;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(loginExecutor, "lockThreshold", 5);
        ReflectionTestUtils.setField(loginExecutor, "lockDuration", Duration.ofMinutes(30));

        loginCmd = new LoginCmd();
        loginCmd.setCredentialType(CredentialTypeEnum.USERNAME);
        loginCmd.setCredentialValue("testuser");
        loginCmd.setPassword("password123");

        credential = new AuthCredential();
        credential.setUserId(1001L);

        authPassword = new AuthPassword();
        authPassword.setUserId(1001L);
        authPassword.setPasswordHash("$2a$10$hashed");
        authPassword.setPasswordStatus(PasswordStatus.VALID);
        authPassword.setFailedAttempts(0);
    }

    @Test
    void execute_withCorrectPassword_shouldLoginSuccessfully() {
        when(authConverter.toDomainCredentialType(CredentialTypeEnum.USERNAME))
                .thenReturn(CredentialType.USERNAME);
        when(authCredentialRepository.findByTypeAndValue(CredentialType.USERNAME, "testuser"))
                .thenReturn(credential);
        when(authPasswordRepository.findByUserId(1001L)).thenReturn(authPassword);
        when(passwordService.matches("password123", "$2a$10$hashed")).thenReturn(true);
        when(tokenService.issueTokenPair(1001L))
                .thenReturn(new TokenPairResult("access-token", "refresh-token", 900L));

        SingleResponse<LoginResultCO> response = loginExecutor.execute(loginCmd);

        assertTrue(response.isSuccess());
        assertNotNull(response.getData());
        assertEquals("access-token", response.getData().getToken().getAccessToken());
        assertEquals("refresh-token", response.getData().getToken().getRefreshToken());
        assertEquals(900L, response.getData().getToken().getExpiresIn());
        assertEquals(1001L, response.getData().getUser().getUserId());

        verify(authPasswordRepository).update(authPassword);
        assertEquals(0, authPassword.getFailedAttempts());
        assertNull(authPassword.getLockedUntil());
    }

    @Test
    void execute_withWrongPassword_shouldIncrementFailedAttempts() {
        when(authConverter.toDomainCredentialType(CredentialTypeEnum.USERNAME))
                .thenReturn(CredentialType.USERNAME);
        when(authCredentialRepository.findByTypeAndValue(CredentialType.USERNAME, "testuser"))
                .thenReturn(credential);
        when(authPasswordRepository.findByUserId(1001L)).thenReturn(authPassword);
        when(passwordService.matches("password123", "$2a$10$hashed")).thenReturn(false);

        SingleResponse<LoginResultCO> response = loginExecutor.execute(loginCmd);

        assertFalse(response.isSuccess());
        assertEquals(AuthErrorCode.CREDENTIAL_NOT_FOUND.getErrCode(), response.getErrCode());

        verify(authPasswordRepository).update(authPassword);
        assertEquals(1, authPassword.getFailedAttempts());
        assertNull(authPassword.getLockedUntil());
    }

    @Test
    void execute_whenReachingLockThreshold_shouldLockAccount() {
        authPassword.setFailedAttempts(4); // 第5次将触发锁定

        when(authConverter.toDomainCredentialType(CredentialTypeEnum.USERNAME))
                .thenReturn(CredentialType.USERNAME);
        when(authCredentialRepository.findByTypeAndValue(CredentialType.USERNAME, "testuser"))
                .thenReturn(credential);
        when(authPasswordRepository.findByUserId(1001L)).thenReturn(authPassword);
        when(passwordService.matches("password123", "$2a$10$hashed")).thenReturn(false);

        SingleResponse<LoginResultCO> response = loginExecutor.execute(loginCmd);

        assertFalse(response.isSuccess());
        assertEquals(AuthErrorCode.ACCOUNT_LOCKED.getErrCode(), response.getErrCode());

        verify(authPasswordRepository).update(authPassword);
        assertEquals(5, authPassword.getFailedAttempts());
        assertNotNull(authPassword.getLockedUntil());
        assertTrue(authPassword.getLockedUntil().isAfter(LocalDateTime.now()));
    }

    @Test
    void execute_whenAccountLocked_shouldReturnAccountLocked() {
        authPassword.setLockedUntil(LocalDateTime.now().plusMinutes(30));

        when(authConverter.toDomainCredentialType(CredentialTypeEnum.USERNAME))
                .thenReturn(CredentialType.USERNAME);
        when(authCredentialRepository.findByTypeAndValue(CredentialType.USERNAME, "testuser"))
                .thenReturn(credential);
        when(authPasswordRepository.findByUserId(1001L)).thenReturn(authPassword);

        SingleResponse<LoginResultCO> response = loginExecutor.execute(loginCmd);

        assertFalse(response.isSuccess());
        assertEquals(AuthErrorCode.ACCOUNT_LOCKED.getErrCode(), response.getErrCode());

        verify(authPasswordRepository, never()).update(any());
        verify(passwordService, never()).matches(anyString(), anyString());
    }

    @Test
    void execute_whenAccountDisabled_shouldReturnAccountDisabled() {
        authPassword.setPasswordStatus(PasswordStatus.EXPIRED);

        when(authConverter.toDomainCredentialType(CredentialTypeEnum.USERNAME))
                .thenReturn(CredentialType.USERNAME);
        when(authCredentialRepository.findByTypeAndValue(CredentialType.USERNAME, "testuser"))
                .thenReturn(credential);
        when(authPasswordRepository.findByUserId(1001L)).thenReturn(authPassword);

        SingleResponse<LoginResultCO> response = loginExecutor.execute(loginCmd);

        assertFalse(response.isSuccess());
        assertEquals(AuthErrorCode.ACCOUNT_DISABLED.getErrCode(), response.getErrCode());

        verify(authPasswordRepository, never()).update(any());
        verify(passwordService, never()).matches(anyString(), anyString());
    }

    @Test
    void execute_whenRedisUnavailable_shouldReturnRedisUnavailable() {
        when(authConverter.toDomainCredentialType(CredentialTypeEnum.USERNAME))
                .thenReturn(CredentialType.USERNAME);
        when(authCredentialRepository.findByTypeAndValue(CredentialType.USERNAME, "testuser"))
                .thenReturn(credential);
        when(authPasswordRepository.findByUserId(1001L)).thenReturn(authPassword);
        when(passwordService.matches("password123", "$2a$10$hashed")).thenReturn(true);
        when(tokenService.issueTokenPair(1001L))
                .thenThrow(TokenServiceException.redisUnavailable());

        SingleResponse<LoginResultCO> response = loginExecutor.execute(loginCmd);

        assertFalse(response.isSuccess());
        assertEquals(AuthErrorCode.REDIS_UNAVAILABLE.getErrCode(), response.getErrCode());
    }

    @Test
    void execute_whenCredentialNotFound_shouldReturnCredentialNotFound() {
        when(authConverter.toDomainCredentialType(CredentialTypeEnum.USERNAME))
                .thenReturn(CredentialType.USERNAME);
        when(authCredentialRepository.findByTypeAndValue(CredentialType.USERNAME, "testuser"))
                .thenReturn(null);

        SingleResponse<LoginResultCO> response = loginExecutor.execute(loginCmd);

        assertFalse(response.isSuccess());
        assertEquals(AuthErrorCode.CREDENTIAL_NOT_FOUND.getErrCode(), response.getErrCode());
    }

    @Test
    void execute_whenPasswordNotSet_shouldReturnCredentialNotFound() {
        when(authConverter.toDomainCredentialType(CredentialTypeEnum.USERNAME))
                .thenReturn(CredentialType.USERNAME);
        when(authCredentialRepository.findByTypeAndValue(CredentialType.USERNAME, "testuser"))
                .thenReturn(credential);
        when(authPasswordRepository.findByUserId(1001L)).thenReturn(null);

        SingleResponse<LoginResultCO> response = loginExecutor.execute(loginCmd);

        assertFalse(response.isSuccess());
        assertEquals(AuthErrorCode.CREDENTIAL_NOT_FOUND.getErrCode(), response.getErrCode());
    }
}
