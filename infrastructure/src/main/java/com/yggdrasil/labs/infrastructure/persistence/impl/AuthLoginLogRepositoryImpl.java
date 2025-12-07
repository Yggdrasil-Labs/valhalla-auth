package com.yggdrasil.labs.infrastructure.persistence.impl;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yggdrasil.labs.domain.auth.model.AuthLoginLog;
import com.yggdrasil.labs.domain.auth.repository.AuthLoginLogRepository;
import com.yggdrasil.labs.infrastructure.persistence.converter.AuthLoginLogConverter;
import com.yggdrasil.labs.infrastructure.persistence.dataobject.AuthLoginLogDO;
import com.yggdrasil.labs.infrastructure.persistence.dataobject.service.AuthLoginLogService;

/**
 * 登录日志仓储实现
 *
 * @author YoungerYang-Y
 */
@Repository
public class AuthLoginLogRepositoryImpl implements AuthLoginLogRepository {

    @Resource private AuthLoginLogService authLoginLogService;

    @Resource private AuthLoginLogConverter authLoginLogConverter;

    @Override
    public AuthLoginLog findByLogId(Long logId) {
        AuthLoginLogDO logDO = authLoginLogService.getById(logId);
        if (logDO == null) {
            return null;
        }
        return authLoginLogConverter.toEntity(logDO);
    }

    @Override
    public List<AuthLoginLog> findByUserId(Long userId) {
        LambdaQueryWrapper<AuthLoginLogDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AuthLoginLogDO::getUserId, userId).orderByDesc(AuthLoginLogDO::getLoginTime);
        List<AuthLoginLogDO> logDOList = authLoginLogService.list(wrapper);
        return logDOList.stream().map(authLoginLogConverter::toEntity).toList();
    }

    @Override
    public List<AuthLoginLog> findByUserIdAndTimeRange(
            Long userId, LocalDateTime startTime, LocalDateTime endTime) {
        LambdaQueryWrapper<AuthLoginLogDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AuthLoginLogDO::getUserId, userId)
                .ge(AuthLoginLogDO::getLoginTime, startTime)
                .le(AuthLoginLogDO::getLoginTime, endTime)
                .orderByDesc(AuthLoginLogDO::getLoginTime);
        List<AuthLoginLogDO> logDOList = authLoginLogService.list(wrapper);
        return logDOList.stream().map(authLoginLogConverter::toEntity).toList();
    }

    @Override
    public List<AuthLoginLog> findByIpAddress(String ipAddress) {
        LambdaQueryWrapper<AuthLoginLogDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AuthLoginLogDO::getLoginIp, ipAddress).orderByDesc(AuthLoginLogDO::getLoginTime);
        List<AuthLoginLogDO> logDOList = authLoginLogService.list(wrapper);
        return logDOList.stream().map(authLoginLogConverter::toEntity).toList();
    }

    @Override
    public void save(AuthLoginLog log) {
        AuthLoginLogDO logDO = authLoginLogConverter.toDO(log);
        authLoginLogService.save(logDO);
    }
}
