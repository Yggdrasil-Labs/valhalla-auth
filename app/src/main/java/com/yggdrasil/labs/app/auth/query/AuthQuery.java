package com.yggdrasil.labs.app.auth.query;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Component;

import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.SingleResponse;
import com.yggdrasil.labs.app.auth.assembler.AuthAssembler;
import com.yggdrasil.labs.app.auth.convert.AuthConverter;
import com.yggdrasil.labs.client.dto.co.AuthUserCO;
import com.yggdrasil.labs.client.dto.co.CredentialCO;
import com.yggdrasil.labs.client.dto.co.TokenCO;
import com.yggdrasil.labs.client.dto.enums.AuthErrorCode;
import com.yggdrasil.labs.client.dto.query.GetTokenQuery;
import com.yggdrasil.labs.client.dto.query.GetUserQuery;
import com.yggdrasil.labs.client.dto.query.ListCredentialsQuery;
import com.yggdrasil.labs.domain.auth.model.AuthCredential;
import com.yggdrasil.labs.domain.auth.model.AuthToken;
import com.yggdrasil.labs.domain.auth.model.AuthUser;
import com.yggdrasil.labs.domain.auth.repository.AuthCredentialRepository;
import com.yggdrasil.labs.domain.auth.repository.AuthTokenRepository;
import com.yggdrasil.labs.domain.auth.repository.AuthUserRepository;

/**
 * 认证查询服务
 *
 * <p>处理所有查询操作
 *
 * @author YoungerYang-Y
 */
@Component
public class AuthQuery {

    @Resource private AuthUserRepository authUserRepository;
    @Resource private AuthCredentialRepository authCredentialRepository;
    @Resource private AuthTokenRepository authTokenRepository;
    @Resource private AuthAssembler authAssembler;

    /** 查询用户信息 */
    public SingleResponse<AuthUserCO> getUser(GetUserQuery query) {
        AuthUser authUser = authUserRepository.findByUserId(query.getUserId());
        if (authUser == null) {
            return SingleResponse.buildFailure(
                    AuthErrorCode.USER_NOT_FOUND.getErrCode(),
                    AuthErrorCode.USER_NOT_FOUND.getErrDesc());
        }
        AuthUserCO userCO = authAssembler.toAuthUserCO(authUser);
        return SingleResponse.of(userCO);
    }

    /** 查询凭证列表 */
    public MultiResponse<CredentialCO> listCredentials(ListCredentialsQuery query) {
        List<AuthCredential> credentials = authCredentialRepository.findByUserId(query.getUserId());
        List<CredentialCO> credentialCOList = authAssembler.toCredentialCOList(credentials);
        return MultiResponse.of(credentialCOList);
    }

    /** 查询 Token 信息 */
    public SingleResponse<TokenCO> getToken(GetTokenQuery query) {
        AuthToken token = null;
        if (query.getTokenHash() != null) {
            token = authTokenRepository.findByTokenHash(query.getTokenHash());
        } else if (query.getJwtId() != null) {
            token = authTokenRepository.findByJwtId(query.getJwtId());
        }

        if (token == null) {
            return SingleResponse.buildFailure(
                    AuthErrorCode.TOKEN_NOT_FOUND.getErrCode(),
                    AuthErrorCode.TOKEN_NOT_FOUND.getErrDesc());
        }

        // 注意：这里不返回完整的 Token 值，只返回元数据
        // 使用 AuthConverter 进行转换
        TokenCO tokenCO = new TokenCO();
        tokenCO.setTokenType(AuthConverter.INSTANCE.toClientTokenType(token.getTokenType()));
        tokenCO.setExpiresAt(token.getExpiresAt());
        tokenCO.setIssuedAt(token.getIssuedAt());
        tokenCO.setDeviceId(token.getDeviceId());
        tokenCO.setDeviceType(token.getDeviceType());
        if (token.getExpiresAt() != null) {
            long expiresIn =
                    Duration.between(LocalDateTime.now(), token.getExpiresAt()).getSeconds();
            tokenCO.setExpiresIn(expiresIn);
        }
        return SingleResponse.of(tokenCO);
    }
}
