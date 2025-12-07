-- Active: 1765029360536@@8.134.198.8@3306@valhalla-auth
-- 认证服务数据库表结构初始化
-- 版本：V1.0.0
-- 创建时间：2025

-- ============================================
-- 1. 用户认证表
-- ============================================
CREATE TABLE `auth_user` (
    `user_id` BIGINT NOT NULL COMMENT '用户ID（主键，关联用户服务，全局唯一，雪花ID）',
    `password_hash` VARCHAR(255) NULL COMMENT '密码哈希值（BCrypt，三方登录可为空）',
    `password_changed_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '密码最后修改时间',
    `last_login_time` DATETIME NULL COMMENT '最后登录时间',
    `last_login_ip` VARCHAR(64) NULL COMMENT '最后登录IP',
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态：1-正常, 2-锁定, 3-禁用, 4-过期',
    `locked_until` DATETIME NULL COMMENT '锁定截止时间',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted_at` BIGINT NOT NULL DEFAULT 0 COMMENT '软删除标记：0-未删除, >0-删除时间戳',
    PRIMARY KEY (`user_id`),
    KEY `idx_deleted_at` (`deleted_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户认证表';

-- ============================================
-- 2. 登录凭证表
-- ============================================
CREATE TABLE `auth_credential` (
    `credential_id` BIGINT NOT NULL COMMENT '凭证ID（主键，雪花ID）',
    `user_id` BIGINT NOT NULL COMMENT '用户ID（关联用户服务，全局唯一）',
    `credential_type` TINYINT NOT NULL COMMENT '凭证类型：1-用户名(USERNAME), 2-手机号(PHONE), 3-邮箱(EMAIL), 4-微信(WECHAT), 5-Google(GOOGLE), 6-其他三方(OTHER)',
    `credential_value` VARCHAR(255) NOT NULL COMMENT '凭证值（用户名/手机号/邮箱/三方ID）',
    `third_party_id` VARCHAR(128) NULL COMMENT '三方登录的唯一ID（仅三方登录时使用）',
    `third_party_name` VARCHAR(64) NULL COMMENT '三方登录名称（如：wechat, google）',
    `is_primary` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否主凭证：0-否, 1-是',
    `verified` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否已验证：0-未验证, 1-已验证',
    `verified_at` DATETIME NULL COMMENT '验证时间',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted_at` BIGINT NOT NULL DEFAULT 0 COMMENT '软删除标记：0-未删除, >0-删除时间戳',
    PRIMARY KEY (`credential_id`),
    UNIQUE KEY `uk_type_value` (`credential_type`, `credential_value`, `deleted_at`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_third_party` (`third_party_name`, `third_party_id`, `deleted_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='登录凭证表';

-- ============================================
-- 3. 多因子认证表（MFA）
-- ============================================
CREATE TABLE `auth_mfa` (
    `mfa_id` BIGINT NOT NULL COMMENT 'MFA ID（主键，雪花ID）',
    `user_id` BIGINT NOT NULL COMMENT '用户ID（关联用户服务）',
    `mfa_type` TINYINT NOT NULL COMMENT 'MFA类型：1-TOTP, 2-短信(SMS), 3-邮箱(EMAIL), 4-U2F',
    `mfa_name` VARCHAR(64) NULL COMMENT 'MFA名称（如：Google Authenticator、备用手机号等）',
    `secret_key` VARCHAR(255) NULL COMMENT '密钥（TOTP密钥、U2F Key等，加密存储）',
    `phone_number` VARCHAR(20) NULL COMMENT '手机号（用于短信验证）',
    `email` VARCHAR(100) NULL COMMENT '邮箱（用于邮箱验证）',
    `u2f_key_handle` VARCHAR(255) NULL COMMENT 'U2F Key Handle',
    `u2f_public_key` TEXT NULL COMMENT 'U2F 公钥',
    `backup_codes` TEXT NULL COMMENT '备用验证码（JSON数组，加密存储）',
    `is_enabled` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否启用：0-未启用, 1-已启用',
    `is_default` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否默认MFA方式：0-否, 1-是',
    `last_used_at` DATETIME NULL COMMENT '最后使用时间',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted_at` BIGINT NOT NULL DEFAULT 0 COMMENT '软删除标记：0-未删除, >0-删除时间戳',
    PRIMARY KEY (`mfa_id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_mfa_type_enabled` (`mfa_type`, `is_enabled`),
    KEY `idx_deleted_at` (`deleted_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='多因子认证表';

-- ============================================
-- 4. 失败尝试表（防爆破）
-- ============================================
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

-- ============================================
-- 5. 登录日志表
-- ============================================
CREATE TABLE `auth_login_log` (
    `log_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '日志ID（主键）',
    `user_id` BIGINT NULL COMMENT '用户ID（关联用户服务，全局唯一）',
    `credential_type` TINYINT NOT NULL COMMENT '登录凭证类型：1-用户名, 2-手机号, 3-邮箱, 4-微信, 5-Google, 6-其他',
    `credential_value` VARCHAR(128) NULL COMMENT '登录凭证值（脱敏处理）',
    `login_type` TINYINT NOT NULL COMMENT '登录类型：1-密码登录(PASSWORD), 2-微信登录(WECHAT), 3-Google登录(GOOGLE), 4-令牌登录(TOKEN), 5-其他',
    `login_status` TINYINT NOT NULL COMMENT '登录状态：1-成功(SUCCESS), 0-失败(FAILED)',
    `mfa_used` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否使用了MFA：0-否, 1-是',
    `mfa_type` TINYINT NULL COMMENT '使用的MFA类型：1-TOTP, 2-短信, 3-邮箱, 4-U2F',
    `fail_reason` VARCHAR(255) NULL COMMENT '失败原因',
    `login_ip` VARCHAR(64) NULL COMMENT '登录IP',
    `user_agent` VARCHAR(512) NULL COMMENT '用户代理（User-Agent）',
    `device_type` VARCHAR(32) NULL COMMENT '设备类型：WEB, MOBILE, API',
    `device_id` VARCHAR(64) NULL COMMENT '设备ID',
    `location` VARCHAR(128) NULL COMMENT '登录地点（根据IP解析）',
    `login_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '登录时间',
    PRIMARY KEY (`log_id`),
    KEY `idx_user_id_time` (`user_id`, `login_time`),
    KEY `idx_credential` (`credential_type`, `credential_value`),
    KEY `idx_login_ip` (`login_ip`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='登录日志表';

-- ============================================
-- 6. Token记录表
-- ============================================
CREATE TABLE `auth_token` (
    `token_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'Token记录ID（主键）',
    `user_id` BIGINT NOT NULL COMMENT '用户ID（关联用户服务，全局唯一）',
    `token_type` TINYINT NOT NULL COMMENT 'Token类型：1-访问令牌(ACCESS), 2-刷新令牌(REFRESH)',
    `token_hash` VARCHAR(255) NOT NULL COMMENT 'Token哈希值（用于查询，不存储完整Token）',
    `jwt_id` VARCHAR(64) NULL COMMENT 'JWT ID（JTI）',
    `device_id` VARCHAR(64) NULL COMMENT '设备ID',
    `device_type` VARCHAR(32) NULL COMMENT '设备类型：WEB, MOBILE, API',
    `device_name` VARCHAR(128) NULL COMMENT '设备名称',
    `ip_address` VARCHAR(64) NULL COMMENT 'IP地址',
    `user_agent` VARCHAR(512) NULL COMMENT '用户代理',
    `location` VARCHAR(128) NULL COMMENT '登录地点（根据IP解析）',
    `issued_at` DATETIME NOT NULL COMMENT '签发时间',
    `expires_at` DATETIME NOT NULL COMMENT '过期时间',
    `last_used_at` DATETIME NULL COMMENT '最后使用时间',
    `revoked` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否已撤销：0-未撤销, 1-已撤销',
    `revoked_at` DATETIME NULL COMMENT '撤销时间',
    `revoke_reason` VARCHAR(255) NULL COMMENT '撤销原因',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`token_id`),
    UNIQUE KEY `uk_jwt_id` (`jwt_id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_token_hash` (`token_hash`),
    KEY `idx_expires_at` (`expires_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Token记录表';
