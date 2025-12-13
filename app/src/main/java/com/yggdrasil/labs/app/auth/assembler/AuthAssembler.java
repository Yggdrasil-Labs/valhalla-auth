package com.yggdrasil.labs.app.auth.assembler;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Component;

import com.yggdrasil.labs.app.auth.convert.AuthConverter;
import com.yggdrasil.labs.client.dto.co.AuthUserCO;
import com.yggdrasil.labs.client.dto.co.CredentialCO;
import com.yggdrasil.labs.client.dto.co.LoginResultCO;
import com.yggdrasil.labs.client.dto.co.TokenCO;
import com.yggdrasil.labs.domain.auth.model.AuthCredential;
import com.yggdrasil.labs.domain.auth.model.AuthToken;
import com.yggdrasil.labs.domain.auth.model.AuthUser;
import com.yggdrasil.labs.domain.auth.model.enums.CredentialType;

/**
 * 认证对象组装器
 *
 * <p>负责将 Domain Entity 组装为 Client CO 对象
 *
 * @author YoungerYang-Y
 */
@Component
public class AuthAssembler {

    @Resource private AuthConverter authConverter;

    /** 组装 AuthUserCO */
    public AuthUserCO toAuthUserCO(AuthUser authUser) {
        if (authUser == null) {
            return null;
        }
        AuthUserCO co = new AuthUserCO();
        co.setUserId(authUser.getUserId());
        co.setStatus(authConverter.toClientUserStatus(authUser.getStatus()));
        co.setLastLoginTime(authUser.getLastLoginTime());
        co.setLastLoginIp(authUser.getLastLoginIp());
        co.setLockedUntil(authUser.getLockedUntil());
        co.setCreateTime(authUser.getCreateTime());
        co.setUpdateTime(authUser.getUpdateTime());
        return co;
    }

    /** 组装 TokenCO */
    public TokenCO toTokenCO(
            AuthToken accessToken,
            AuthToken refreshToken,
            String accessTokenValue,
            String refreshTokenValue) {
        if (accessToken == null) {
            return null;
        }
        TokenCO co = new TokenCO();
        co.setAccessToken(accessTokenValue);
        co.setRefreshToken(refreshTokenValue);
        co.setTokenType(authConverter.toClientTokenType(accessToken.getTokenType()));
        if (accessToken.getExpiresAt() != null) {
            Duration duration = Duration.between(LocalDateTime.now(), accessToken.getExpiresAt());
            co.setExpiresIn(duration.getSeconds());
            co.setExpiresAt(accessToken.getExpiresAt());
        }
        co.setIssuedAt(accessToken.getIssuedAt());
        co.setDeviceId(accessToken.getDeviceId());
        co.setDeviceType(accessToken.getDeviceType());
        return co;
    }

    /** 组装 TokenCO（仅 Access Token） */
    public TokenCO toTokenCO(AuthToken accessToken, String accessTokenValue) {
        return toTokenCO(accessToken, null, accessTokenValue, null);
    }

    /** 组装 LoginResultCO */
    public LoginResultCO toLoginResultCO(AuthUser authUser, TokenCO tokenCO) {
        LoginResultCO co = new LoginResultCO();
        co.setUser(toAuthUserCO(authUser));
        co.setToken(tokenCO);
        return co;
    }

    /** 组装 CredentialCO */
    public CredentialCO toCredentialCO(AuthCredential credential) {
        if (credential == null) {
            return null;
        }
        CredentialCO co = new CredentialCO();
        co.setCredentialId(credential.getCredentialId());
        co.setUserId(credential.getUserId());
        co.setCredentialType(authConverter.toClientCredentialType(credential.getCredentialType()));
        // 凭证值脱敏处理
        co.setCredentialValue(
                maskCredentialValue(
                        credential.getCredentialValue(), credential.getCredentialType()));
        co.setThirdPartyId(credential.getThirdPartyId());
        co.setThirdPartyName(credential.getThirdPartyName());
        co.setIsPrimary(credential.getIsPrimary());
        co.setVerified(credential.getVerified());
        co.setVerifiedAt(credential.getVerifiedAt());
        co.setCreateTime(credential.getCreateTime());
        co.setUpdateTime(credential.getUpdateTime());
        return co;
    }

    /** 组装 CredentialCO 列表 */
    public List<CredentialCO> toCredentialCOList(List<AuthCredential> credentials) {
        if (credentials == null) {
            return List.of();
        }
        return credentials.stream().map(this::toCredentialCO).toList();
    }

    /** 凭证值脱敏处理 */
    private String maskCredentialValue(String credentialValue, CredentialType credentialType) {
        if (credentialValue == null) {
            return null;
        }
        if (credentialType == null) {
            return credentialValue;
        }
        // 根据类型进行脱敏
        switch (credentialType) {
            case PHONE:
                // 手机号：138****5678
                if (credentialValue.length() == 11) {
                    return credentialValue.substring(0, 3) + "****" + credentialValue.substring(7);
                }
                break;
            case EMAIL:
                // 邮箱：abc***@example.com
                int atIndex = credentialValue.indexOf('@');
                if (atIndex > 0) {
                    String prefix = credentialValue.substring(0, Math.min(3, atIndex));
                    return prefix + "***" + credentialValue.substring(atIndex);
                }
                break;
            default:
                // 其他类型不脱敏或简单脱敏
                break;
        }
        return credentialValue;
    }
}
