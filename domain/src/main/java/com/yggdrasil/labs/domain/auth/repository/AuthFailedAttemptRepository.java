package com.yggdrasil.labs.domain.auth.repository;

import java.time.LocalDateTime;
import java.util.List;

import com.yggdrasil.labs.domain.auth.model.AuthFailedAttempt;
import com.yggdrasil.labs.domain.auth.model.enums.CredentialType;

/**
 * 失败尝试仓储接口
 *
 * @author YoungerYang-Y
 */
public interface AuthFailedAttemptRepository {

    /** 根据尝试ID查找 */
    AuthFailedAttempt findByAttemptId(Long attemptId);

    /** 根据凭证类型、凭证值和IP地址查找最近的失败尝试 */
    List<AuthFailedAttempt> findByCredentialAndIp(
            CredentialType credentialType,
            String credentialValue,
            String ipAddress,
            LocalDateTime startTime);

    /** 根据用户ID查找最近的失败尝试 */
    List<AuthFailedAttempt> findByUserId(Long userId, LocalDateTime startTime);

    /** 根据IP地址查找最近的失败尝试 */
    List<AuthFailedAttempt> findByIpAddress(String ipAddress, LocalDateTime startTime);

    /** 统计指定时间范围内的失败尝试次数 */
    long countByCredentialAndIp(
            CredentialType credentialType,
            String credentialValue,
            String ipAddress,
            LocalDateTime startTime);

    /** 保存失败尝试记录 */
    void save(AuthFailedAttempt attempt);
}
