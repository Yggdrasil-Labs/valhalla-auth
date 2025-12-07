package com.yggdrasil.labs.infrastructure.persistence.impl;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.yggdrasil.labs.domain.auth.model.AuthUser;
import com.yggdrasil.labs.domain.auth.repository.AuthUserRepository;
import com.yggdrasil.labs.infrastructure.persistence.converter.AuthUserConverter;
import com.yggdrasil.labs.infrastructure.persistence.dataobject.AuthUserDO;
import com.yggdrasil.labs.infrastructure.persistence.dataobject.service.AuthUserService;

/**
 * 用户认证仓储实现
 *
 * @author YoungerYang-Y
 */
@Repository
public class AuthUserRepositoryImpl implements AuthUserRepository {

    @Resource private AuthUserService authUserService;

    @Resource private AuthUserConverter authUserConverter;

    @Override
    public AuthUser findByUserId(Long userId) {
        AuthUserDO authUserDO = authUserService.getById(userId);
        if (authUserDO == null) {
            return null;
        }
        return authUserConverter.toEntity(authUserDO);
    }

    @Override
    public void save(AuthUser authUser) {
        AuthUserDO authUserDO = authUserConverter.toDO(authUser);
        authUserService.save(authUserDO);
    }

    @Override
    public void update(AuthUser authUser) {
        AuthUserDO authUserDO = authUserConverter.toDO(authUser);
        authUserService.updateById(authUserDO);
    }

    @Override
    public void delete(Long userId) {
        authUserService.removeById(userId);
    }
}
