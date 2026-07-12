# valhalla-auth 数据库表结构

<!-- ⚠️ 本文件由工具自动生成，请勿手动编辑 -->

Last generated: 2026-07-12

---

## auth_password — 用户密码表

存储用户密码相关信息，一个用户对应一条密码记录。

| 字段名 | 类型 | 约束 | 默认值 | 说明 |
|--------|------|------|--------|------|
| user_id | BIGINT | PRIMARY KEY, NOT NULL | — | 用户ID（主键，关联用户服务，全局唯一） |
| password_hash | VARCHAR(512) | NOT NULL | — | 密码哈希值（BCrypt等） |
| password_algo | TINYINT | NOT NULL | 1 | 密码算法：1-BCrypt, 2-Argon2id, 3-PBKDF2等 |
| password_version | INT | NOT NULL | 1 | 密码策略版本（用于密码升级） |
| password_status | TINYINT | NOT NULL | 1 | 密码状态：1-有效, 2-已过期, 3-需重置, 4-临时密码 |
| force_change | TINYINT(1) | NOT NULL | 0 | 是否强制改密：0-否, 1-是 |
| password_expires_at | DATETIME | NULL | NULL | 密码过期时间 |
| changed_at | DATETIME | NOT NULL | CURRENT_TIMESTAMP | 最后修改时间 |
| create_time | DATETIME | NOT NULL | CURRENT_TIMESTAMP | 创建时间 |
| update_time | DATETIME | NOT NULL | CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP | 更新时间 |

**索引：**

| 索引名 | 类型 | 字段 |
|--------|------|------|
| PRIMARY | PRIMARY KEY | user_id |

---

## auth_credential — 登录凭证表

支持多种登录方式：用户名、手机号、邮箱、三方登录（OAuth）。一个用户可以有多个登录凭证。

| 字段名 | 类型 | 约束 | 默认值 | 说明 |
|--------|------|------|--------|------|
| credential_id | BIGINT | PRIMARY KEY, NOT NULL | — | 凭证ID（主键，雪花ID） |
| user_id | BIGINT | NOT NULL | — | 用户ID（关联用户服务，全局唯一） |
| credential_type | TINYINT | NOT NULL | — | 凭证类型：1-USERNAME, 2-PHONE, 3-EMAIL, 4-OAUTH |
| credential_value | VARCHAR(255) | NOT NULL | — | 登录唯一值（username/phone/email/provider_user_id） |
| provider | VARCHAR(64) | NULL | NULL | 三方提供方：wechat/google/github（仅 OAUTH） |
| is_primary | TINYINT(1) | NOT NULL | 0 | 是否主凭证：0-否, 1-是 |
| verified | TINYINT(1) | NOT NULL | 0 | 是否已验证：0-未验证, 1-已验证 |
| verified_at | DATETIME | NULL | NULL | 验证时间 |
| create_time | DATETIME | NOT NULL | CURRENT_TIMESTAMP | 创建时间 |
| update_time | DATETIME | NOT NULL | CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP | 更新时间 |
| deleted_at | BIGINT | NOT NULL | 0 | 软删除标记：0-未删除, >0-删除时间戳 |

**索引：**

| 索引名 | 类型 | 字段 |
|--------|------|------|
| PRIMARY | PRIMARY KEY | credential_id |
| uk_credential | UNIQUE | (credential_type, credential_value, deleted_at) |
| idx_user | INDEX | (user_id) |
| idx_provider | INDEX | (provider, credential_value, deleted_at) |

---

## auth_mfa_factor — 多因子认证表（MFA因子）

支持 TOTP、短信验证码、邮箱验证码、U2F Key 配置。一个用户可以有多个 MFA 因子。

| 字段名 | 类型 | 约束 | 默认值 | 说明 |
|--------|------|------|--------|------|
| mfa_id | BIGINT | PRIMARY KEY, NOT NULL | — | MFA ID（主键，雪花ID） |
| user_id | BIGINT | NOT NULL | — | 用户ID（关联用户服务） |
| mfa_type | TINYINT | NOT NULL | — | MFA类型：1-TOTP, 2-短信(SMS), 3-邮箱(EMAIL), 4-U2F |
| mfa_name | VARCHAR(64) | NULL | NULL | MFA名称（如：Google Authenticator、备用手机号等） |
| secret_key | VARCHAR(255) | NULL | NULL | 密钥（TOTP密钥、U2F Key等，加密存储） |
| phone_number | VARCHAR(20) | NULL | NULL | 手机号（用于短信验证） |
| email | VARCHAR(100) | NULL | NULL | 邮箱（用于邮箱验证） |
| u2f_key_handle | VARCHAR(255) | NULL | NULL | U2F Key Handle |
| u2f_public_key | TEXT | NULL | NULL | U2F 公钥 |
| backup_codes | TEXT | NULL | NULL | 备用验证码（JSON数组，加密存储） |
| status | TINYINT | NOT NULL | 1 | 因子状态：1-启用, 2-禁用 |
| max_attempts | INT | NOT NULL | 5 | 最大验证尝试次数（用于防暴力破解，配置值，实际计数在Redis） |
| is_default | TINYINT(1) | NOT NULL | 0 | 是否默认MFA方式：0-否, 1-是 |
| last_used_at | DATETIME | NULL | NULL | 最后使用时间 |
| create_time | DATETIME | NOT NULL | CURRENT_TIMESTAMP | 创建时间 |
| update_time | DATETIME | NOT NULL | CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP | 更新时间 |
| deleted_at | BIGINT | NOT NULL | 0 | 软删除标记：0-未删除, >0-删除时间戳 |

**索引：**

| 索引名 | 类型 | 字段 |
|--------|------|------|
| PRIMARY | PRIMARY KEY | mfa_id |
| idx_user_id | INDEX | (user_id) |
| idx_mfa_type_status | INDEX | (mfa_type, status) |
| idx_deleted_at | INDEX | (deleted_at) |
