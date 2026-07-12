# DOMAINS.md

## 领域概述

valhalla-auth 围绕**身份认证**这一核心领域，管理用户登录凭证、密码安全和多因子认证。服务不存储用户画像信息，仅关注"证明你是谁"这一安全边界。

## 领域划分

### 1. 凭证管理（Credential）

**职责**：管理用户的多种登录标识，支持一个用户绑定多个凭证。

**核心实体**：`AuthCredential`

**业务规则**：
- 同一 `credential_type + credential_value` 组合全局唯一（软删除场景通过 `deleted_at` 区分）
- 每个用户至少有一个主凭证（`is_primary = true`）
- 主凭证不可删除
- OAuth 凭证必须携带 `provider` 字段

**凭证类型**：
| 类型 | 代码 | 说明 |
|------|------|------|
| USERNAME | 1 | 用户名登录 |
| PHONE | 2 | 手机号登录 |
| EMAIL | 3 | 邮箱登录 |
| OAUTH | 4 | 三方登录（微信/Google/GitHub） |

### 2. 密码管理（Password）

**职责**：管理用户密码的哈希存储、状态控制和生命周期。

**核心实体**：`AuthPassword`

**业务规则**：
- 密码使用 BCrypt 算法（默认），支持 Argon2id / PBKDF2 升级
- `password_version` 跟踪策略版本，支持透明密码升级
- `force_change` 标记管理员创建用户时生成的初始密码，用户首次登录后必须修改
- `password_expires_at` 支持密码过期策略
- 密码状态：VALID → EXPIRED → RESET → TEMPORARY

### 3. 多因子认证（MFA）

**职责**：管理 MFA 因子配置和验证流程。

**核心实体**：`AuthMfaFactor`

**业务规则**：
- 支持 TOTP（如 Google Authenticator）、SMS、EMAIL、U2F 四种因子类型
- 每个用户可配置多个 MFA 因子，其中一个为默认方式
- `max_attempts` 限制验证尝试次数，实际计数存储在 Redis
- `secret_key` 和 `backup_codes` 加密存储

### 4. Token 生命周期（Token）

**职责**：JWT Access/Refresh Token 的签发、验证、刷新和吊销。

**业务规则**：
- Access Token 短有效期，Refresh Token 长有效期
- Token 元数据缓存于 Redis，支持主动吊销（黑名单机制）
- 刷新 Token 时旧 Refresh Token 立即失效（Rotation）
- 登出时吊销所有关联 Token

### 5. 用户初始化（User Initialization）

**职责**：为 valhalla-user 服务提供用户认证凭证初始化入口。

**交互方式**：通过 Dubbo RPC 接口 `AuthRpcFacade.initializeUser` 调用。

**支持场景**：
- 管理员创建：USERNAME + 自动生成密码
- 用户密码注册：USERNAME/PHONE/EMAIL + 用户设定密码
- 手机/邮箱注册：PHONE/EMAIL + 验证状态 + 可选密码
- OAuth 注册：OAUTH + provider（不需要密码）

## 领域边界

```
┌─────────────────────────────────────────────────────┐
│                  valhalla-auth                        │
│                                                      │
│  ┌──────────────┐  ┌──────────────┐  ┌───────────┐ │
│  │   Credential │  │   Password   │  │    MFA    │ │
│  │   Management │  │   Management │  │  Factors  │ │
│  └──────────────┘  └──────────────┘  └───────────┘ │
│                                                      │
│  ┌──────────────┐  ┌──────────────────────────────┐ │
│  │    Token     │  │  User Initialization (RPC)   │ │
│  │  Lifecycle   │  │                              │ │
│  └──────────────┘  └──────────────────────────────┘ │
└─────────────────────────────────────────────────────┘
          │                         ▲
          │ REST API                │ Dubbo RPC
          ▼                         │
   ┌─────────────┐          ┌──────────────┐
   │   Clients   │          │ valhalla-user │
   │ (Web/App)   │          │              │
   └─────────────┘          └──────────────┘
```

## 与其他服务的关系

| 服务 | 关系 | 交互方式 |
|------|------|----------|
| valhalla-user | 用户创建时调用 auth 初始化凭证 | Dubbo RPC（auth 提供 `AuthRpcFacade`） |
| 前端/客户端 | 登录、刷新 Token、登出 | REST API（`/api/v1/auth/**`） |
| 网关 | Token 验证 | REST API（`/api/v1/auth/verify`） |
