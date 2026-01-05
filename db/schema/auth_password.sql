-- 密码表
-- 存储用户密码相关信息
-- 一个用户对应一条密码记录

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
