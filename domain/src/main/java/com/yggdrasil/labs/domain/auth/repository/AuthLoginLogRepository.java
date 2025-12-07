package com.yggdrasil.labs.domain.auth.repository;

import java.time.LocalDateTime;
import java.util.List;

import com.yggdrasil.labs.domain.auth.model.AuthLoginLog;

/**
 * 登录日志仓储接口
 *
 * @author YoungerYang-Y
 */
public interface AuthLoginLogRepository {

    /** 根据日志ID查找 */
    AuthLoginLog findByLogId(Long logId);

    /** 根据用户ID查找登录日志 */
    List<AuthLoginLog> findByUserId(Long userId);

    /** 根据用户ID和时间范围查找登录日志 */
    List<AuthLoginLog> findByUserIdAndTimeRange(
            Long userId, LocalDateTime startTime, LocalDateTime endTime);

    /** 根据IP地址查找登录日志 */
    List<AuthLoginLog> findByIpAddress(String ipAddress);

    /** 保存登录日志 */
    void save(AuthLoginLog log);
}
