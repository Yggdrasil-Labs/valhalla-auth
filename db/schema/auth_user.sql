-- 用户认证表
-- 存储用户认证相关信息（密码等）
-- 注意：用户详细信息由 valhalla-user 服务管理，此表仅存储认证相关数据
-- 注意：登录凭证（用户名、手机号、邮箱等）由 auth_credential 表管理

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
