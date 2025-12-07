-- 登录日志表
-- 记录所有登录尝试（成功/失败）

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
