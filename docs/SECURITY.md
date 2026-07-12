# SECURITY.md

## 安全边界

valhalla-auth 是平台的安全核心，管理所有身份验证相关的敏感数据。安全设计遵循纵深防御原则。

## 敏感数据分类

| 数据 | 级别 | 存储方式 | 备注 |
|------|------|----------|------|
| 密码哈希 | 最高 | MySQL `auth_password.password_hash` | BCrypt 单向哈希，不可逆 |
| MFA 密钥 | 最高 | MySQL `auth_mfa_factor.secret_key` | 加密存储 |
| MFA 备用码 | 高 | MySQL `auth_mfa_factor.backup_codes` | JSON 数组，加密存储 |
| JWT Token | 高 | Redis 缓存 | 有效期控制 + 主动吊销 |
| 凭证值 | 中 | MySQL `auth_credential.credential_value` | 用户名/手机号/邮箱 |
| U2F 公钥 | 中 | MySQL `auth_mfa_factor.u2f_public_key` | 公钥本身不是秘密 |

## 密码安全策略

### 哈希算法

- **默认**：BCrypt（`password_algo = 1`）
- **升级路径**：Argon2id（`password_algo = 2`）→ PBKDF2（`password_algo = 3`）
- `password_version` 字段支持透明升级：用户下次登录时重新哈希并更新版本

### 密码生命周期

- `password_status`：VALID → EXPIRED → RESET → TEMPORARY
- `force_change = true`：管理员创建用户后，首次登录强制改密
- `password_expires_at`：支持密码过期策略（可选配置）

### 暴力破解防护（设计方案，尚未实现）

- MFA 验证尝试计数存储在 Redis（`max_attempts` 默认 5 次）
- 登录失败计数通过 Redis 实现，避免数据库压力
- 超过阈值后触发账户锁定或延迟响应

## Token 安全

### JWT 设计

- Access Token：短有效期，携带最小权限声明
- Refresh Token：长有效期，严格绑定设备/会话
- Token Rotation：刷新时旧 Refresh Token 立即失效

### Token 吊销

- Redis 存储 Token 元数据，支持主动吊销
- 登出时清除用户所有有效 Token
- 管理员可强制吊销指定用户的所有会话

## 传输安全

- REST API 监听 8081 端口，生产环境通过反向代理终止 TLS
- Dubbo RPC 监听 20880 端口，内网通信不暴露公网
- Actuator 端点仅暴露 `health` 和 `info`，敏感详情需授权访问

## 数据库安全

- 软删除设计：`deleted_at` 字段保留审计痕迹
- 唯一约束包含 `deleted_at`，防止软删除后的数据冲突
- 生产环境数据库凭证通过 Nacos 配置中心加密管理

## 服务间通信安全

- Dubbo RPC 使用 Nacos 命名空间隔离（`dev`/`test`/`prod`）
- RPC 接口版本化（`version = "1.0.0"`）和分组（`group = "auth"`）
- 配置中心连接使用环境变量注入，不硬编码地址

## 安全审计

- 登录事件记录（成功/失败）
- 密码修改事件记录
- Token 签发/吊销事件记录
- 异常访问模式检测（未来通过 Redis 计数实现）

## 已知风险与待办

| 风险 | 状态 | 缓解措施 |
|------|------|----------|
| 密码验证逻辑尚未实现 | TODO | LoginExecutor 中标注 TODO |
| JWT 签发逻辑尚未实现 | TODO | 需集成 JWT 库 |
| Token Redis 缓存尚未实现 | TODO | 当前 getToken 返回 NOT_IMPLEMENTED |
| MFA 验证流程尚未实现 | TODO | 数据模型已就绪，业务逻辑待开发 |
