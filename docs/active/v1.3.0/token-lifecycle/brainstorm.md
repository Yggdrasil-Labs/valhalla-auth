# Token 生命周期管理

## 背景

valhalla-auth 认证服务当前 Token 相关功能均为 TODO 状态（TD1~TD4）。需要实现完整的 JWT Token 生命周期：登录签发、验证、刷新、主动吊销。

## 已确认的设计决策

### Token 存储策略

**JWT + Redis 元数据**：JWT 签发后在 Redis 存储元数据（jti、过期时间、用户ID），兼顾无状态验证性能和主动吊销能力。

### 双令牌生命周期

- Access Token：15 分钟有效期
- Refresh Token：7 天有效期
- 刷新策略：Refresh 时仅签发新 AT，RT 不轮换

### 签名算法

**HS256（对称密钥）**：内部微服务信任域，密钥通过 Nacos 配置中心分发。

### Redis 降级策略

**优雅降级**：

- AT 验证：Redis 不可用时退化为纯 JWT 本地校验（跳过黑名单检查）
- 登录/刷新/登出：Redis 不可用时返回错误（写操作不降级）

### JWT Claims（最小化）

`sub`(用户ID) + `jti`(Token唯一ID) + `iat` + `exp` + `type`(access/refresh)

## 流程设计

### 1. 登录流程（LoginExecutor）

```
请求 → Adapter (AuthController.login)
     → App (LoginExecutor.execute)
       1. 通过 credentialType + identifier 查询 AuthCredential
       2. 未找到 → 返回"用户名或密码错误"（不区分用户不存在和密码错误）
       3. 检查凭证状态（is_enabled、locked_until）
       4. 通过 credential.user_id 查询 AuthPassword
       5. BCrypt.verify(输入密码, 存储 hash)
       6. 验证失败 → 递增失败计数，检查是否触发锁定
       7. 验证成功 → 重置失败计数
       8. 调用 TokenService.issueTokenPair(userId)
       9. 返回 LoginCO { accessToken, refreshToken, expiresIn }
```

- 密码验证在 app 层完成（调用 domain 的 AuthPassword 实体方法）
- Token 签发委托给独立的 TokenService（app 层接口，基础设施层实现）
- 登录失败计数暂存数据库（后续可迁移到 Redis）



### 2. Token 签发与存储（TokenService）

```
TokenService.issueTokenPair(userId):
  1. 生成 jti（UUID）
  2. 构建 Access Token JWT:
     - sub: userId, jti: jti, type: "access"
     - iat: now, exp: now + 15min
     - 签名: HS256(secret)
  3. 构建 Refresh Token JWT:
     - sub: userId, jti: 独立 UUID, type: "refresh"
     - iat: now, exp: now + 7d
     - 签名: HS256(secret)
  4. Redis 存储元数据:
     - "token:access:{jti}" → { userId, exp }  TTL=15min
     - "token:refresh:{rt_jti}" → { userId, exp }  TTL=7d
     - "user:tokens:{userId}" → SET { at_jti, rt_jti }
  5. 返回 TokenPair { accessToken, refreshToken, expiresIn: 900 }
```

- TokenService 接口定义在 app 层，实现在 infrastructure 层
- HS256 密钥从 Nacos 读取（`auth.jwt.secret`）
- `user:tokens:{userId}` SET 支持"登出所有设备"
- Redis 写入失败 → 登录整体失败



### 3. Token 验证（VerifyTokenExecutor）

```
VerifyTokenExecutor.execute(token):
  1. 本地 JWT 校验（签名 + 过期 + type=access）
  2. 提取 jti，查询 Redis "token:access:{jti}"
     - Redis 可用 + key 不存在 → TOKEN_REVOKED
     - Redis 可用 + key 存在 → 验证通过
     - Redis 不可用 → 跳过吊销检查（优雅降级）
  3. 返回 VerifyTokenCO { valid: true, userId, expiresAt }
```

- 验证分两层：本地 JWT（必过）+ Redis 黑名单（尽力而为）
- 通过 Dubbo RPC 暴露给 bifrost-gateway
- 高频路径，不做数据库查询



### 4. Token 刷新（RefreshTokenExecutor）

```
RefreshTokenExecutor.execute(refreshToken):
  1. 本地 JWT 校验（签名 + 过期 + type=refresh）
  2. 查 Redis "token:refresh:{rt_jti}"（不存在 → 已吊销，Redis 不可用 → 拒绝）
  3. 签发新 AT（新 jti），写入 Redis
  4. 旧 AT 自然过期，无需主动删除
  5. 返回 RefreshCO { accessToken, expiresIn: 900 }
```

- RT 不轮换，原 RT 继续有效
- Redis 必须可用（写操作）



### 5. 登出与主动吊销

```
LogoutExecutor.execute(accessToken):
  1. 解析 JWT 提取 jti 和 userId（允许已过期的 AT）
  2. 删除 AT/RT 的 Redis key + 清空 user:tokens SET
  3. Redis 不可用 → 返回错误

LogoutAllExecutor.execute(userId):
  1. 获取 user:tokens SET 中所有 jti
  2. 批量删除所有 Redis key + 清空 SET
```

- 登出接受已过期的 AT（仍需吊销 RT）
- 吊销后，验证环节通过 key 不存在识别已吊销状态



## Redis Key 结构总览


| Key                    | 类型          | TTL     | 用途                    |
| ---------------------- | ----------- | ------- | --------------------- |
| `token:access:{jti}`   | String/Hash | 15min   | AT 元数据，验证时查存在性        |
| `token:refresh:{jti}`  | String/Hash | 7d      | RT 元数据，刷新时查存在性        |
| `user:tokens:{userId}` | Set         | 无（手动管理） | 用户所有活跃 Token 的 jti 集合 |




## 技术依赖


| 组件                       | 用途          | 引入方式               |
| ------------------------ | ----------- | ------------------ |
| jjwt (io.jsonwebtoken)   | JWT 签发与解析   | Maven 依赖           |
| mimir-boot-starter-redis | Redis 连接与操作 | mimir-boot Starter |
| Nacos                    | JWT 密钥配置分发  | 已有                 |




## 需求摘要（sdd-ready）



### 目标

为 valhalla-auth 实现完整的 JWT Token 生命周期管理，覆盖登录签发、验证、刷新、主动吊销全流程。

### 用户故事

- As a 平台用户, I want 通过账号密码登录获取 Token, so that 我能访问受保护的 API
- As a 客户端, I want 在 AT 过期前使用 RT 刷新, so that 用户无需重复登录
- As a 网关服务, I want 通过 RPC 验证 Token 有效性, so that 我能拦截非法请求
- As a 用户, I want 登出后 Token 立即失效, so that 我的账户安全得到保障
- As a 管理员, I want 强制登出某用户所有设备, so that 我能应对账户安全事件



### 验收标准

- [ ] AC1: 正确的用户名+密码登录返回 accessToken + refreshToken + expiresIn
- [ ] AC2: 错误密码返回明确错误码，连续失败 5 次触发账户锁定
- [ ] AC3: 有效 AT 通过 verifyToken RPC 返回 valid=true + userId
- [ ] AC4: 过期/签名错误/已吊销的 AT 验证返回对应错误码
- [ ] AC5: 有效 RT 刷新返回新 AT，原 RT 不变
- [ ] AC6: 已吊销 RT 刷新返回错误
- [ ] AC7: 登出后 AT 和 RT 均不可用
- [ ] AC8: LogoutAll 吊销该用户所有设备的 Token
- [ ] AC9: Redis 不可用时，AT 验证仍可通过（纯 JWT 校验）
- [ ] AC10: Redis 不可用时，登录/刷新/登出返回服务不可用错误



### 非功能需求

- 性能：Token 验证 P99 < 50ms（本地 JWT 校验 + Redis GET）
- 安全：密钥不硬编码，通过 Nacos 配置注入；JWT 密钥 ≥ 256 位



### 已确认的技术决策

- 存储策略：JWT + Redis 元数据
- 签名算法：HS256
- 令牌有效期：AT 15min / RT 7d，RT 不轮换
- Claims：最小化（sub + jti + iat + exp + type）
- 降级：验证降级放行，写操作不降级
- 架构：TokenService 接口在 app 层，实现在 infrastructure 层
- 依赖：jjwt + mimir-boot-starter-redis



### 不做的事（Scope Out）

- 微信/OAuth 第三方登录（后续迭代）
- MFA 多因子验证（数据模型已就绪，业务流程后续实现）
- RT 轮换（当前 RT 不轮换，后续可升级）
- Token 黑名单持久化到数据库（仅 Redis）



### 下一步

→ 使用 sdd skill 将本摘要正式化为 spec.md / design.md / plan.md

## Decisions


| 决策点                  | 选项                                  | 选择  | 理由                                            |
| -------------------- | ----------------------------------- | --- | --------------------------------------------- |
| `user:tokens` SET 膨胀 | A 惰性清理 / B 主动清理 / C ZSET+定时         | A   | 正常使用下膨胀有限（7 天内最多几十个），过度优化不值得                  |
| 登出时定位 RT             | A 带前缀 / B AT 元数据存 rtJti / C 客户端传 RT | A   | SET 成员格式 `at:{jti}` / `rt:{jti}`，过滤清晰，不依赖客户端  |
| HS256 密钥轮换           | A 不做 / B 双密钥滚动 / C 版本化              | A   | 15min AT + Redis 全量清空已足够应对泄露，后续迭代再升级          |
| 并发登录策略               | A 不限制 / B 限制最大会话数 / C 单设备独占         | B   | 安全可控，Nacos 可配（`auth.token.max-sessions`，默认 5） |
| 超限踢出策略               | A FIFO 踢最早 / B 拒绝新登录                | A   | 用户体验好，`user:tokens` 改为 ZSET（score=签发时间戳）支持排序  |
| 登录失败计数并发             | A 乐观锁 / B 悲观锁 / C Redis INCR        | A   | 项目已有 @Version 模式，零额外成本，Redis 不可用时仍可计数         |




### 结构调整

基于以上决策，Redis Key 结构调整为：


| Key                    | 类型          | TTL   | 成员格式                                | 用途                  |
| ---------------------- | ----------- | ----- | ----------------------------------- | ------------------- |
| `token:access:{jti}`   | String | 15min | 值: `{userId}:{rtJti}`                | AT 元数据 + 关联 RT 定位   |
| `token:refresh:{jti}`  | String | 7d    | 值: `userId`                          | RT 元数据              |
| `user:tokens:{userId}` | **ZSET**    | 无     | `at:{jti}` / `rt:{jti}`，score=签发时间戳 | 用户活跃会话管理、会话数限制、登出定位 |




### 新增配置项


| 配置                        | 默认值 | 说明                         |
| ------------------------- | --- | -------------------------- |
| `auth.token.max-sessions` | 5   | 单用户最大并发会话数，超出时 FIFO 踢出最早会话 |




### 补充 Scope Out

- 密钥轮换（双密钥滚动）— 后续迭代
