-- 多因子认证表（MFA因子）
-- 支持 TOTP、短信验证码、邮箱验证码、U2F Key 配置
-- 一个用户可以有多个 MFA 因子

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

