-- 失败尝试表（防爆破）
-- 记录登录失败尝试，用于防止暴力破解攻击

CREATE TABLE `auth_failed_attempt` (
    `attempt_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '尝试ID（主键）',
    `user_id` BIGINT NULL COMMENT '用户ID（可为空）',
    `credential_type` TINYINT NOT NULL COMMENT '凭证类型：1-用户名, 2-手机号, 3-邮箱, 4-微信, 5-Google, 6-其他',
    `credential_value` VARCHAR(128) NOT NULL COMMENT '尝试的凭证值（用户名/手机号/邮箱等）',
    `ip_address` VARCHAR(64) NOT NULL COMMENT 'IP地址',
    `user_agent` VARCHAR(512) NULL COMMENT '用户代理',
    `device_fingerprint` VARCHAR(128) NULL COMMENT '设备指纹',
    `fail_reason` VARCHAR(255) NULL COMMENT '失败原因',
    `attempt_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '尝试时间',
    PRIMARY KEY (`attempt_id`),
    KEY `idx_credential_ip` (`credential_type`, `credential_value`, `ip_address`),
    KEY `idx_attempt_time` (`attempt_time`),
    KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='失败尝试表（防爆破）';
