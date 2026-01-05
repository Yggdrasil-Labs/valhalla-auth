package com.yggdrasil.labs.infrastructure.persistence.impl;

import java.util.List;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yggdrasil.labs.domain.auth.model.AuthMfaFactor;
import com.yggdrasil.labs.domain.auth.model.enums.MfaFactorStatus;
import com.yggdrasil.labs.domain.auth.model.enums.MfaType;
import com.yggdrasil.labs.domain.auth.repository.AuthMfaFactorRepository;
import com.yggdrasil.labs.infrastructure.persistence.converter.AuthMfaFactorConverter;
import com.yggdrasil.labs.infrastructure.persistence.dataobject.AuthMfaFactorDO;
import com.yggdrasil.labs.infrastructure.persistence.dataobject.service.AuthMfaFactorService;

/**
 * 多因子认证因子仓储实现
 *
 * @author YoungerYang-Y
 */
@Repository
public class AuthMfaFactorRepositoryImpl implements AuthMfaFactorRepository {

    @Resource private AuthMfaFactorService authMfaFactorService;

    @Resource private AuthMfaFactorConverter authMfaFactorConverter;

    @Override
    public AuthMfaFactor findByMfaId(Long mfaId) {
        AuthMfaFactorDO factorDO = authMfaFactorService.getById(mfaId);
        if (factorDO == null) {
            return null;
        }
        return authMfaFactorConverter.toEntity(factorDO);
    }

    @Override
    public List<AuthMfaFactor> findByUserId(Long userId) {
        LambdaQueryWrapper<AuthMfaFactorDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AuthMfaFactorDO::getUserId, userId);
        List<AuthMfaFactorDO> factorDOList = authMfaFactorService.list(wrapper);
        return factorDOList.stream().map(authMfaFactorConverter::toEntity).toList();
    }

    @Override
    public List<AuthMfaFactor> findByUserIdAndType(Long userId, MfaType mfaType) {
        LambdaQueryWrapper<AuthMfaFactorDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AuthMfaFactorDO::getUserId, userId)
                .eq(AuthMfaFactorDO::getMfaType, mfaType.getCode());
        List<AuthMfaFactorDO> factorDOList = authMfaFactorService.list(wrapper);
        return factorDOList.stream().map(authMfaFactorConverter::toEntity).toList();
    }

    @Override
    public List<AuthMfaFactor> findByUserIdAndStatus(Long userId, MfaFactorStatus status) {
        LambdaQueryWrapper<AuthMfaFactorDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AuthMfaFactorDO::getUserId, userId)
                .eq(AuthMfaFactorDO::getStatus, status.getCode());
        List<AuthMfaFactorDO> factorDOList = authMfaFactorService.list(wrapper);
        return factorDOList.stream().map(authMfaFactorConverter::toEntity).toList();
    }

    @Override
    public AuthMfaFactor findDefaultByUserId(Long userId) {
        LambdaQueryWrapper<AuthMfaFactorDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AuthMfaFactorDO::getUserId, userId)
                .eq(AuthMfaFactorDO::getIsDefault, true)
                .last("LIMIT 1");
        AuthMfaFactorDO factorDO = authMfaFactorService.getOne(wrapper);
        if (factorDO == null) {
            return null;
        }
        return authMfaFactorConverter.toEntity(factorDO);
    }

    @Override
    public void save(AuthMfaFactor factor) {
        AuthMfaFactorDO factorDO = authMfaFactorConverter.toDO(factor);
        authMfaFactorService.save(factorDO);
    }

    @Override
    public void update(AuthMfaFactor factor) {
        AuthMfaFactorDO factorDO = authMfaFactorConverter.toDO(factor);
        authMfaFactorService.updateById(factorDO);
    }

    @Override
    public void delete(Long mfaId) {
        authMfaFactorService.removeById(mfaId);
    }
}
