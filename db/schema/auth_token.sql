-- Token 表
-- 存储 Token 信息（用于审计和追踪，主要存储于 Redis）
-- 注意：Token 主要存储在 Redis 中，此表用于持久化记录和审计

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
