package com.yggdrasil.labs.infrastructure.persistence.impl;

import java.util.List;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yggdrasil.labs.domain.auth.model.AuthMfa;
import com.yggdrasil.labs.domain.auth.model.enums.MfaType;
import com.yggdrasil.labs.domain.auth.repository.AuthMfaRepository;
import com.yggdrasil.labs.infrastructure.persistence.converter.AuthMfaConverter;
import com.yggdrasil.labs.infrastructure.persistence.dataobject.AuthMfaDO;
import com.yggdrasil.labs.infrastructure.persistence.dataobject.service.AuthMfaService;

/**
 * 多因子认证仓储实现
 *
 * @author YoungerYang-Y
 */
@Repository
public class AuthMfaRepositoryImpl implements AuthMfaRepository {

    @Resource private AuthMfaService authMfaService;

    @Resource private AuthMfaConverter authMfaConverter;

    @Override
    public AuthMfa findByMfaId(Long mfaId) {
        AuthMfaDO mfaDO = authMfaService.getById(mfaId);
        if (mfaDO == null) {
            return null;
        }
        return authMfaConverter.toEntity(mfaDO);
    }

    @Override
    public List<AuthMfa> findByUserId(Long userId) {
        LambdaQueryWrapper<AuthMfaDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AuthMfaDO::getUserId, userId);
        List<AuthMfaDO> mfaDOList = authMfaService.list(wrapper);
        return mfaDOList.stream().map(authMfaConverter::toEntity).toList();
    }

    @Override
    public List<AuthMfa> findByUserIdAndType(Long userId, MfaType mfaType) {
        LambdaQueryWrapper<AuthMfaDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AuthMfaDO::getUserId, userId).eq(AuthMfaDO::getMfaType, mfaType.getCode());
        List<AuthMfaDO> mfaDOList = authMfaService.list(wrapper);
        return mfaDOList.stream().map(authMfaConverter::toEntity).toList();
    }

    @Override
    public List<AuthMfa> findEnabledByUserId(Long userId) {
        LambdaQueryWrapper<AuthMfaDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AuthMfaDO::getUserId, userId).eq(AuthMfaDO::getIsEnabled, true);
        List<AuthMfaDO> mfaDOList = authMfaService.list(wrapper);
        return mfaDOList.stream().map(authMfaConverter::toEntity).toList();
    }

    @Override
    public AuthMfa findDefaultByUserId(Long userId) {
        LambdaQueryWrapper<AuthMfaDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AuthMfaDO::getUserId, userId).eq(AuthMfaDO::getIsDefault, true).last("LIMIT 1");
        AuthMfaDO mfaDO = authMfaService.getOne(wrapper);
        if (mfaDO == null) {
            return null;
        }
        return authMfaConverter.toEntity(mfaDO);
    }

    @Override
    public void save(AuthMfa mfa) {
        AuthMfaDO mfaDO = authMfaConverter.toDO(mfa);
        authMfaService.save(mfaDO);
    }

    @Override
    public void update(AuthMfa mfa) {
        AuthMfaDO mfaDO = authMfaConverter.toDO(mfa);
        authMfaService.updateById(mfaDO);
    }

    @Override
    public void delete(Long mfaId) {
        authMfaService.removeById(mfaId);
    }
}
