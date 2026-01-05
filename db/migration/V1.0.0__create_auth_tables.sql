-- Active: 1765029360536@@8.134.198.8@3306@valhalla-auth
-- 认证服务数据库表结构初始化
-- 版本：V1.0.0
-- 创建时间：2025

-- ============================================
-- 1. 密码表
-- ============================================
CREATE TABLE `auth_password` (
    `user_id` BIGINT NOT NULL COMMENT '用户ID（主键，关联用户服务，全局唯一）',
    `password_hash` VARCHAR(512) NOT NULL COMMENT '密码哈希值（BCrypt等）',
    `password_algo` TINYINT NOT NULL DEFAULT 1 COMMENT '密码算法：1-BCrypt, 2-Argon2id, 3-PBKDF2等',
    `password_version` INT NOT NULL DEFAULT 1 COMMENT '密码策略版本（用于密码升级）',
    `password_status` TINYINT NOT NULL DEFAULT 1 COMMENT '密码状态：1-有效, 2-已过期, 3-需重置, 4-临时密码',
    `force_change` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否强制改密：0-否, 1-是',
    `password_expires_at` DATETIME NULL COMMENT '密码过期时间',
    `changed_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后修改时间',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户密码表';

-- ============================================
-- 2. 登录凭证表
-- ============================================
CREATE TABLE `auth_credential` (
    `credential_id` BIGINT NOT NULL COMMENT '凭证ID（主键，雪花ID）',
    `user_id` BIGINT NOT NULL COMMENT '用户ID（关联用户服务，全局唯一）',
    `credential_type` TINYINT NOT NULL COMMENT '凭证类型：1-USERNAME, 2-PHONE, 3-EMAIL, 4-OAUTH',
    `credential_value` VARCHAR(255) NOT NULL COMMENT '登录唯一值（username/phone/email/provider_user_id）',
    `provider` VARCHAR(64) NULL COMMENT '三方提供方：wechat/google/github（仅 OAUTH）',
    `is_primary` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否主凭证：0-否, 1-是',
    `verified` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否已验证：0-未验证, 1-已验证',
    `verified_at` DATETIME NULL COMMENT '验证时间',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted_at` BIGINT NOT NULL DEFAULT 0 COMMENT '软删除标记：0-未删除, >0-删除时间戳',
    PRIMARY KEY (`credential_id`),
    UNIQUE KEY `uk_credential` (`credential_type`, `credential_value`, `deleted_at`),
    KEY `idx_user` (`user_id`),
    KEY `idx_provider` (`provider`, `credential_value`, `deleted_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='登录凭证表';

-- ============================================
-- 3. 多因子认证表（MFA因子）
-- ============================================
CREATE TABLE `auth_mfa_factor` (
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
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '因子状态：1-启用, 2-禁用',
    `max_attempts` INT NOT NULL DEFAULT 5 COMMENT '最大验证尝试次数（用于防暴力破解，配置值，实际计数在Redis）',
    `is_default` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否默认MFA方式：0-否, 1-是',
    `last_used_at` DATETIME NULL COMMENT '最后使用时间',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted_at` BIGINT NOT NULL DEFAULT 0 COMMENT '软删除标记：0-未删除, >0-删除时间戳',
    PRIMARY KEY (`mfa_id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_mfa_type_status` (`mfa_type`, `status`),
    KEY `idx_deleted_at` (`deleted_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='多因子认证表（MFA因子）';

