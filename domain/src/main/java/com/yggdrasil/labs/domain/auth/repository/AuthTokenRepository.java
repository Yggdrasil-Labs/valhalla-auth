package com.yggdrasil.labs.domain.auth.repository;

import java.util.List;

import com.yggdrasil.labs.domain.auth.model.AuthToken;

/**
 * Token仓储接口
 *
 * @author YoungerYang-Y
 */
public interface AuthTokenRepository {

    /** 根据Token ID查找 */
    AuthToken findByTokenId(Long tokenId);

    /** 根据Token哈希值查找 */
    AuthToken findByTokenHash(String tokenHash);

    /** 根据JWT ID查找 */
    AuthToken findByJwtId(String jwtId);

    /** 根据用户ID查找所有Token */
    List<AuthToken> findByUserId(Long userId);

    /** 根据用户ID和设备ID查找Token */
    List<AuthToken> findByUserIdAndDeviceId(Long userId, String deviceId);

    /** 保存Token记录 */
    void save(AuthToken token);

    /** 更新Token记录 */
    void update(AuthToken token);

    /** 删除Token记录 */
    void delete(Long tokenId);
}
