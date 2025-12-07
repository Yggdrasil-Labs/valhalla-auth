-- 登录凭证表
-- 支持多种登录方式：用户名、手机号、邮箱、三方登录（微信/Google等）
-- 一个用户可以有多个登录凭证

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
