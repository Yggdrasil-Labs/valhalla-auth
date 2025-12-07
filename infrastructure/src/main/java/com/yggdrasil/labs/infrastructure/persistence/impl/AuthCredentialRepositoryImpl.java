package com.yggdrasil.labs.infrastructure.persistence.impl;

import java.util.List;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yggdrasil.labs.domain.auth.model.AuthCredential;
import com.yggdrasil.labs.domain.auth.model.enums.CredentialType;
import com.yggdrasil.labs.domain.auth.repository.AuthCredentialRepository;
import com.yggdrasil.labs.infrastructure.persistence.converter.AuthCredentialConverter;
import com.yggdrasil.labs.infrastructure.persistence.dataobject.AuthCredentialDO;
import com.yggdrasil.labs.infrastructure.persistence.dataobject.service.AuthCredentialService;

/**
 * 登录凭证仓储实现
 *
 * @author YoungerYang-Y
 */
@Repository
public class AuthCredentialRepositoryImpl implements AuthCredentialRepository {

    @Resource private AuthCredentialService authCredentialService;

    @Resource private AuthCredentialConverter authCredentialConverter;

    @Override
    public AuthCredential findByCredentialId(Long credentialId) {
        AuthCredentialDO credentialDO = authCredentialService.getById(credentialId);
        if (credentialDO == null) {
            return null;
        }
        return authCredentialConverter.toEntity(credentialDO);
    }

    @Override
    public List<AuthCredential> findByUserId(Long userId) {
        LambdaQueryWrapper<AuthCredentialDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AuthCredentialDO::getUserId, userId);
        List<AuthCredentialDO> credentialDOList = authCredentialService.list(wrapper);
        return credentialDOList.stream().map(authCredentialConverter::toEntity).toList();
    }

    @Override
    public AuthCredential findByTypeAndValue(
            CredentialType credentialType, String credentialValue) {
        LambdaQueryWrapper<AuthCredentialDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AuthCredentialDO::getCredentialType, credentialType.getCode())
                .eq(AuthCredentialDO::getCredentialValue, credentialValue);
        AuthCredentialDO credentialDO = authCredentialService.getOne(wrapper);
        if (credentialDO == null) {
            return null;
        }
        return authCredentialConverter.toEntity(credentialDO);
    }

    @Override
    public AuthCredential findByThirdParty(String thirdPartyName, String thirdPartyId) {
        LambdaQueryWrapper<AuthCredentialDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AuthCredentialDO::getThirdPartyName, thirdPartyName)
                .eq(AuthCredentialDO::getThirdPartyId, thirdPartyId);
        AuthCredentialDO credentialDO = authCredentialService.getOne(wrapper);
        if (credentialDO == null) {
            return null;
        }
        return authCredentialConverter.toEntity(credentialDO);
    }

    @Override
    public void save(AuthCredential credential) {
        AuthCredentialDO credentialDO = authCredentialConverter.toDO(credential);
        authCredentialService.save(credentialDO);
    }

    @Override
    public void update(AuthCredential credential) {
        AuthCredentialDO credentialDO = authCredentialConverter.toDO(credential);
        authCredentialService.updateById(credentialDO);
    }

    @Override
    public void delete(Long credentialId) {
        authCredentialService.removeById(credentialId);
    }
}
