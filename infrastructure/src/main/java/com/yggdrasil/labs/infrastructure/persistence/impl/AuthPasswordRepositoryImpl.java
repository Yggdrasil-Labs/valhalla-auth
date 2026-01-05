package com.yggdrasil.labs.infrastructure.persistence.impl;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.yggdrasil.labs.domain.auth.model.AuthPassword;
import com.yggdrasil.labs.domain.auth.repository.AuthPasswordRepository;
import com.yggdrasil.labs.infrastructure.persistence.converter.AuthPasswordConverter;
import com.yggdrasil.labs.infrastructure.persistence.dataobject.AuthPasswordDO;
import com.yggdrasil.labs.infrastructure.persistence.dataobject.service.AuthPasswordService;

/**
 * 用户密码仓储实现
 *
 * @author YoungerYang-Y
 */
@Repository
public class AuthPasswordRepositoryImpl implements AuthPasswordRepository {

    @Resource private AuthPasswordService authPasswordService;

    @Resource private AuthPasswordConverter authPasswordConverter;

    @Override
    public AuthPassword findByUserId(Long userId) {
        AuthPasswordDO passwordDO = authPasswordService.getById(userId);
        if (passwordDO == null) {
            return null;
        }
        return authPasswordConverter.toEntity(passwordDO);
    }

    @Override
    public void save(AuthPassword password) {
        AuthPasswordDO passwordDO = authPasswordConverter.toDO(password);
        authPasswordService.save(passwordDO);
    }

    @Override
    public void update(AuthPassword password) {
        AuthPasswordDO passwordDO = authPasswordConverter.toDO(password);
        authPasswordService.updateById(passwordDO);
    }
}
