package com.yggdrasil.labs.domain.auth.repository;

import java.util.List;

import com.yggdrasil.labs.domain.auth.model.AuthCredential;
import com.yggdrasil.labs.domain.auth.model.enums.CredentialType;

/**
 * 登录凭证仓储接口
 *
 * @author YoungerYang-Y
 */
public interface AuthCredentialRepository {

    /** 根据凭证ID查找 */
    AuthCredential findByCredentialId(Long credentialId);

    /** 根据用户ID查找所有凭证 */
    List<AuthCredential> findByUserId(Long userId);

    /** 根据凭证类型和凭证值查找 */
    AuthCredential findByTypeAndValue(CredentialType credentialType, String credentialValue);

    /** 根据三方登录信息查找 */
    AuthCredential findByThirdParty(String thirdPartyName, String thirdPartyId);

    /** 保存凭证 */
    void save(AuthCredential credential);

    /** 更新凭证 */
    void update(AuthCredential credential);

    /** 删除凭证（软删除） */
    void delete(Long credentialId);
}
