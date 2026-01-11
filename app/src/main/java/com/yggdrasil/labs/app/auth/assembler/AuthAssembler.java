package com.yggdrasil.labs.app.auth.assembler;

import java.util.List;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Component;

import com.yggdrasil.labs.app.auth.convert.AuthConverter;
import com.yggdrasil.labs.app.auth.dto.co.CredentialCO;
import com.yggdrasil.labs.app.auth.dto.co.LoginResultCO;
import com.yggdrasil.labs.app.auth.dto.co.TokenCO;
import com.yggdrasil.labs.app.auth.dto.co.UserInitializationCO;
import com.yggdrasil.labs.app.auth.dto.enums.TokenTypeEnum;
import com.yggdrasil.labs.domain.auth.model.AuthCredential;
import com.yggdrasil.labs.domain.auth.model.enums.CredentialType;

/**
 * 认证对象组装器
 *
 * <p>负责将 Domain Entity 组装为 App 层 CO 对象
 *
 * @author YoungerYang-Y
 */
@Component
public class AuthAssembler {

    @Resource private AuthConverter authConverter;

    /** 组装 TokenCO */
    public TokenCO toTokenCO(
            String accessTokenValue,
            String refreshTokenValue,
            TokenTypeEnum tokenType,
            Long expiresIn,
            java.time.LocalDateTime expiresAt,
            java.time.LocalDateTime issuedAt,
            String deviceId,
            String deviceType) {
        TokenCO co = new TokenCO();
        co.setAccessToken(accessTokenValue);
        co.setRefreshToken(refreshTokenValue);
        co.setTokenType(tokenType);
        co.setExpiresIn(expiresIn);
        co.setExpiresAt(expiresAt);
        co.setIssuedAt(issuedAt);
        co.setDeviceId(deviceId);
        co.setDeviceType(deviceType);
        return co;
    }

    /** 组装 LoginResultCO */
    public LoginResultCO toLoginResultCO(Long userId, TokenCO tokenCO) {
        LoginResultCO co = new LoginResultCO();
        // TODO: 如果需要用户信息，可能需要调用用户服务获取
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
        // TODO: CredentialCO 需要添加 provider 字段，暂时保留 thirdPartyId 和 thirdPartyName 以保持兼容
        // co.setProvider(credential.getProvider());
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

    /** 组装 UserInitializationCO（用于密码场景） */
    public UserInitializationCO toUserInitializationCO(
            AuthCredential credential, String plainPassword) {
        if (credential == null) {
            return null;
        }
        UserInitializationCO co = new UserInitializationCO();
        co.setCredentialId(credential.getCredentialId());
        co.setCredentialType(authConverter.toClientCredentialType(credential.getCredentialType()));
        co.setCredentialValue(credential.getCredentialValue());
        co.setProvider(credential.getProvider());
        co.setInitialPassword(plainPassword);
        co.setForceChangePassword(true);
        return co;
    }

    /** 组装 UserInitializationCO（用于 OAuth 场景） */
    public UserInitializationCO toUserInitializationCO(AuthCredential credential) {
        if (credential == null) {
            return null;
        }
        UserInitializationCO co = new UserInitializationCO();
        co.setCredentialId(credential.getCredentialId());
        co.setCredentialType(authConverter.toClientCredentialType(credential.getCredentialType()));
        co.setCredentialValue(credential.getCredentialValue());
        co.setProvider(credential.getProvider());
        // OAuth 场景不包含密码信息
        co.setInitialPassword(null);
        co.setForceChangePassword(null);
        return co;
    }
}
