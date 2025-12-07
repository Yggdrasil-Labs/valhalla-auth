package com.yggdrasil.labs.infrastructure.persistence.impl;

import java.util.List;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yggdrasil.labs.domain.auth.model.AuthToken;
import com.yggdrasil.labs.domain.auth.repository.AuthTokenRepository;
import com.yggdrasil.labs.infrastructure.persistence.converter.AuthTokenConverter;
import com.yggdrasil.labs.infrastructure.persistence.dataobject.AuthTokenDO;
import com.yggdrasil.labs.infrastructure.persistence.dataobject.service.AuthTokenService;

/**
 * Token仓储实现
 *
 * @author YoungerYang-Y
 */
@Repository
public class AuthTokenRepositoryImpl implements AuthTokenRepository {

    @Resource private AuthTokenService authTokenService;

    @Resource private AuthTokenConverter authTokenConverter;

    @Override
    public AuthToken findByTokenId(Long tokenId) {
        AuthTokenDO tokenDO = authTokenService.getById(tokenId);
        if (tokenDO == null) {
            return null;
        }
        return authTokenConverter.toEntity(tokenDO);
    }

    @Override
    public AuthToken findByTokenHash(String tokenHash) {
        LambdaQueryWrapper<AuthTokenDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AuthTokenDO::getTokenHash, tokenHash).last("LIMIT 1");
        AuthTokenDO tokenDO = authTokenService.getOne(wrapper);
        if (tokenDO == null) {
            return null;
        }
        return authTokenConverter.toEntity(tokenDO);
    }

    @Override
    public AuthToken findByJwtId(String jwtId) {
        LambdaQueryWrapper<AuthTokenDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AuthTokenDO::getJwtId, jwtId).last("LIMIT 1");
        AuthTokenDO tokenDO = authTokenService.getOne(wrapper);
        if (tokenDO == null) {
            return null;
        }
        return authTokenConverter.toEntity(tokenDO);
    }

    @Override
    public List<AuthToken> findByUserId(Long userId) {
        LambdaQueryWrapper<AuthTokenDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AuthTokenDO::getUserId, userId);
        List<AuthTokenDO> tokenDOList = authTokenService.list(wrapper);
        return tokenDOList.stream().map(authTokenConverter::toEntity).toList();
    }

    @Override
    public List<AuthToken> findByUserIdAndDeviceId(Long userId, String deviceId) {
        LambdaQueryWrapper<AuthTokenDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AuthTokenDO::getUserId, userId).eq(AuthTokenDO::getDeviceId, deviceId);
        List<AuthTokenDO> tokenDOList = authTokenService.list(wrapper);
        return tokenDOList.stream().map(authTokenConverter::toEntity).toList();
    }

    @Override
    public void save(AuthToken token) {
        AuthTokenDO tokenDO = authTokenConverter.toDO(token);
        authTokenService.save(tokenDO);
    }

    @Override
    public void update(AuthToken token) {
        AuthTokenDO tokenDO = authTokenConverter.toDO(token);
        authTokenService.updateById(tokenDO);
    }

    @Override
    public void delete(Long tokenId) {
        authTokenService.removeById(tokenId);
    }
}
