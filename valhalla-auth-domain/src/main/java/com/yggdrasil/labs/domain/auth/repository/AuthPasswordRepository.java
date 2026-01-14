package com.yggdrasil.labs.domain.auth.repository;

import com.yggdrasil.labs.domain.auth.model.AuthPassword;

/**
 * 用户密码仓储接口
 *
 * @author YoungerYang-Y
 */
public interface AuthPasswordRepository {

    /** 根据用户ID查找 */
    AuthPassword findByUserId(Long userId);

    /** 保存密码信息 */
    void save(AuthPassword password);

    /** 更新密码信息 */
    void update(AuthPassword password);
}
