package com.yggdrasil.labs.domain.auth.repository;

import com.yggdrasil.labs.domain.auth.model.AuthUser;

/**
 * 用户认证仓储接口
 *
 * @author YoungerYang-Y
 */
public interface AuthUserRepository {

    /** 根据用户ID查找 */
    AuthUser findByUserId(Long userId);

    /** 保存用户认证信息 */
    void save(AuthUser authUser);

    /** 更新用户认证信息 */
    void update(AuthUser authUser);

    /** 删除用户认证信息（软删除） */
    void delete(Long userId);
}
