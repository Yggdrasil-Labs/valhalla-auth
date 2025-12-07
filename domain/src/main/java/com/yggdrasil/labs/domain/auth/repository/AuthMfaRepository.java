package com.yggdrasil.labs.domain.auth.repository;

import java.util.List;

import com.yggdrasil.labs.domain.auth.model.AuthMfa;
import com.yggdrasil.labs.domain.auth.model.enums.MfaType;

/**
 * 多因子认证仓储接口
 *
 * @author YoungerYang-Y
 */
public interface AuthMfaRepository {

    /** 根据MFA ID查找 */
    AuthMfa findByMfaId(Long mfaId);

    /** 根据用户ID查找所有MFA配置 */
    List<AuthMfa> findByUserId(Long userId);

    /** 根据用户ID和MFA类型查找 */
    List<AuthMfa> findByUserIdAndType(Long userId, MfaType mfaType);

    /** 查找用户已启用的MFA配置 */
    List<AuthMfa> findEnabledByUserId(Long userId);

    /** 查找用户的默认MFA配置 */
    AuthMfa findDefaultByUserId(Long userId);

    /** 保存MFA配置 */
    void save(AuthMfa mfa);

    /** 更新MFA配置 */
    void update(AuthMfa mfa);

    /** 删除MFA配置（软删除） */
    void delete(Long mfaId);
}
