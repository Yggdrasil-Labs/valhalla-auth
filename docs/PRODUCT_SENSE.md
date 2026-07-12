# PRODUCT_SENSE.md

## 产品定位

valhalla-auth 是英灵殿平台的"门卫"——所有进入系统的用户必须经过本服务的身份验证。它不关心用户是谁（那是 valhalla-user 的事），只关心"你能证明你是你吗"。

## 用户画像

| 角色 | 使用方式 | 核心诉求 |
|------|----------|----------|
| 管理员 | 通过 valhalla-user-admin 后台创建用户 | 用户初始化流程可靠，初始密码安全 |
| 终端用户 | 通过前端应用登录/登出 | 登录快、Token 不频繁过期、多设备支持 |
| 前端应用 | 调用 REST API 进行认证 | 接口响应快、错误信息明确 |
| valhalla-user 服务 | 通过 Dubbo RPC 初始化用户认证凭证 | RPC 调用可靠、幂等 |
| API 网关 | 调用 verify 接口验证 Token | 延迟极低、高可用 |

## 核心业务流程

### 1. 用户创建（RPC 触发）

```
valhalla-user 创建用户
    → Dubbo RPC: AuthRpcFacade.initializeUser
    → 创建 auth_credential（主凭证）
    → 创建 auth_password（初始密码，force_change=true）
    → 返回凭证信息 + 初始密码明文
```

### 2. 用户登录

```
前端提交凭证（用户名/手机/邮箱 + 密码）
    → AuthController.login
    → 查找凭证 → 查找密码 → 验证密码
    → 签发 Access Token + Refresh Token
    → Token 元数据写入 Redis
    → 返回 Token + 用户基本信息
```

### 3. Token 刷新

```
前端 Access Token 过期
    → AuthController.refreshToken
    → 验证 Refresh Token 有效性
    → 旧 Refresh Token 失效（Rotation）
    → 签发新 Access + Refresh Token
    → 返回新 Token
```

### 4. Token 验证

```
API 网关收到请求
    → AuthController.verifyToken
    → 从 Redis 查找 Token 元数据
    → 验证未过期、未吊销
    → 返回用户ID和基本信息
```

### 5. 用户登出

```
前端发起登出
    → AuthController.logout
    → 吊销用户所有有效 Token（Redis 删除）
    → 返回成功
```

## 产品约束

- **密码不存储明文**：即使数据库泄露，攻击者也无法还原密码
- **Token 可吊销**：不依赖 JWT 过期时间，支持管理员强制踢人
- **多凭证绑定**：同一用户可用用户名、手机号、邮箱任一方式登录
- **认证与用户解耦**：auth 不存储用户画像（姓名、头像等），通过 userId 关联

## 产品演进方向

| 阶段 | 能力 | 状态 |
|------|------|------|
| MVP | 凭证管理 + 用户初始化 RPC | ✅ 已完成 |
| Phase 1 | 密码验证 + JWT 签发 + 登录/登出 | 🚧 TODO（框架就绪，核心逻辑待实现） |
| Phase 2 | Token Redis 缓存 + 刷新 + 吊销 | 🚧 TODO |
| Phase 3 | MFA 多因子认证（TOTP/SMS/Email） | 📋 已建模，待实现 |
| Phase 4 | OAuth 三方登录接入 | 📋 数据模型就绪 |
| Phase 5 | 登录风控（异地登录检测、设备指纹） | 📋 远期规划 |

## 与平台全局的关系

```
┌────────────────────────────────────────────┐
│             Valhalla 平台                   │
│                                            │
│  valhalla-user-admin (前端后台)             │
│       │                                    │
│       ▼                                    │
│  valhalla-user (用户管理)                   │
│       │                                    │
│       │ Dubbo RPC: initializeUser          │
│       ▼                                    │
│  valhalla-auth (认证服务) ◄── 本服务        │
│       │                                    │
│       │ REST API: login/verify/refresh     │
│       ▼                                    │
│  前端应用 / API 网关                        │
└────────────────────────────────────────────┘
```
