package com.yggdrasil.labs.domain.auth.repository;

import java.util.List;

import com.yggdrasil.labs.domain.auth.model.AuthMfaFactor;
import com.yggdrasil.labs.domain.auth.model.enums.MfaFactorStatus;
import com.yggdrasil.labs.domain.auth.model.enums.MfaType;

/**
 * 多因子认证因子仓储接口
 *
 * @author YoungerYang-Y
 */
public interface AuthMfaFactorRepository {

    /** 根据MFA ID查找 */
    AuthMfaFactor findByMfaId(Long mfaId);

    /** 根据用户ID查找所有MFA因子 */
    List<AuthMfaFactor> findByUserId(Long userId);

    /** 根据用户ID和MFA类型查找 */
    List<AuthMfaFactor> findByUserIdAndType(Long userId, MfaType mfaType);

    /** 根据用户ID和状态查找 */
    List<AuthMfaFactor> findByUserIdAndStatus(Long userId, MfaFactorStatus status);

    /** 查找用户的默认MFA因子 */
    AuthMfaFactor findDefaultByUserId(Long userId);

    /** 保存MFA因子 */
    void save(AuthMfaFactor factor);

    /** 更新MFA因子 */
    void update(AuthMfaFactor factor);

    /** 删除MFA因子（软删除） */
    void delete(Long mfaId);
}
