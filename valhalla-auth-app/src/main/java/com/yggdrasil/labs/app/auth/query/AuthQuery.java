package com.yggdrasil.labs.app.auth.query;

import java.util.List;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Component;

import com.alibaba.cola.dto.MultiResponse;
import com.yggdrasil.labs.app.auth.assembler.AuthAssembler;
import com.yggdrasil.labs.app.auth.dto.co.CredentialCO;
import com.yggdrasil.labs.app.auth.dto.query.ListCredentialsQuery;
import com.yggdrasil.labs.domain.auth.model.AuthCredential;
import com.yggdrasil.labs.domain.auth.repository.AuthCredentialRepository;

/**
 * 认证查询服务
 *
 * <p>处理所有查询操作
 *
 * @author YoungerYang-Y
 */
@Component
public class AuthQuery {

    @Resource private AuthCredentialRepository authCredentialRepository;
    @Resource private AuthAssembler authAssembler;

    /** 查询凭证列表 */
    public MultiResponse<CredentialCO> listCredentials(ListCredentialsQuery query) {
        List<AuthCredential> credentials = authCredentialRepository.findByUserId(query.getUserId());
        List<CredentialCO> credentialCOList = authAssembler.toCredentialCOList(credentials);
        return MultiResponse.of(credentialCOList);
    }

    /** 查询 Token 信息 */
    // TODO: Token 信息现在存储在 Redis 中，需要从 Redis 查询
    // public SingleResponse<TokenCO> getToken(GetTokenQuery query) {
    //     // 从 Redis 查询 Token 信息
    //     // ...
    // }
}
