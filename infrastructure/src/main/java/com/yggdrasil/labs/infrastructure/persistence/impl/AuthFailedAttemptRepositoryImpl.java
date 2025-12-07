package com.yggdrasil.labs.infrastructure.persistence.impl;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yggdrasil.labs.domain.auth.model.AuthFailedAttempt;
import com.yggdrasil.labs.domain.auth.model.enums.CredentialType;
import com.yggdrasil.labs.domain.auth.repository.AuthFailedAttemptRepository;
import com.yggdrasil.labs.infrastructure.persistence.converter.AuthFailedAttemptConverter;
import com.yggdrasil.labs.infrastructure.persistence.dataobject.AuthFailedAttemptDO;
import com.yggdrasil.labs.infrastructure.persistence.dataobject.service.AuthFailedAttemptService;

/**
 * 失败尝试仓储实现
 *
 * @author YoungerYang-Y
 */
@Repository
public class AuthFailedAttemptRepositoryImpl implements AuthFailedAttemptRepository {

    @Resource private AuthFailedAttemptService authFailedAttemptService;

    @Resource private AuthFailedAttemptConverter authFailedAttemptConverter;

    @Override
    public AuthFailedAttempt findByAttemptId(Long attemptId) {
        AuthFailedAttemptDO attemptDO = authFailedAttemptService.getById(attemptId);
        if (attemptDO == null) {
            return null;
        }
        return authFailedAttemptConverter.toEntity(attemptDO);
    }

    @Override
    public List<AuthFailedAttempt> findByCredentialAndIp(
            CredentialType credentialType,
            String credentialValue,
            String ipAddress,
            LocalDateTime startTime) {
        LambdaQueryWrapper<AuthFailedAttemptDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AuthFailedAttemptDO::getCredentialType, credentialType.getCode())
                .eq(AuthFailedAttemptDO::getCredentialValue, credentialValue)
                .eq(AuthFailedAttemptDO::getIpAddress, ipAddress)
                .ge(AuthFailedAttemptDO::getAttemptTime, startTime)
                .orderByDesc(AuthFailedAttemptDO::getAttemptTime);
        List<AuthFailedAttemptDO> attemptDOList = authFailedAttemptService.list(wrapper);
        return attemptDOList.stream().map(authFailedAttemptConverter::toEntity).toList();
    }

    @Override
    public List<AuthFailedAttempt> findByUserId(Long userId, LocalDateTime startTime) {
        LambdaQueryWrapper<AuthFailedAttemptDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AuthFailedAttemptDO::getUserId, userId)
                .ge(AuthFailedAttemptDO::getAttemptTime, startTime)
                .orderByDesc(AuthFailedAttemptDO::getAttemptTime);
        List<AuthFailedAttemptDO> attemptDOList = authFailedAttemptService.list(wrapper);
        return attemptDOList.stream().map(authFailedAttemptConverter::toEntity).toList();
    }

    @Override
    public List<AuthFailedAttempt> findByIpAddress(String ipAddress, LocalDateTime startTime) {
        LambdaQueryWrapper<AuthFailedAttemptDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AuthFailedAttemptDO::getIpAddress, ipAddress)
                .ge(AuthFailedAttemptDO::getAttemptTime, startTime)
                .orderByDesc(AuthFailedAttemptDO::getAttemptTime);
        List<AuthFailedAttemptDO> attemptDOList = authFailedAttemptService.list(wrapper);
        return attemptDOList.stream().map(authFailedAttemptConverter::toEntity).toList();
    }

    @Override
    public long countByCredentialAndIp(
            CredentialType credentialType,
            String credentialValue,
            String ipAddress,
            LocalDateTime startTime) {
        LambdaQueryWrapper<AuthFailedAttemptDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AuthFailedAttemptDO::getCredentialType, credentialType.getCode())
                .eq(AuthFailedAttemptDO::getCredentialValue, credentialValue)
                .eq(AuthFailedAttemptDO::getIpAddress, ipAddress)
                .ge(AuthFailedAttemptDO::getAttemptTime, startTime);
        return authFailedAttemptService.count(wrapper);
    }

    @Override
    public void save(AuthFailedAttempt attempt) {
        AuthFailedAttemptDO attemptDO = authFailedAttemptConverter.toDO(attempt);
        authFailedAttemptService.save(attemptDO);
    }
}
