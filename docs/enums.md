# 枚举值统一说明文档

## 概述

本文档统一描述 Valhalla Auth 项目中所有枚举类型的定义、用途和值说明。枚举按层次结构分类。

## Client 层枚举

### RpcCredentialTypeEnum

**包路径**: `com.yggdrasil.labs.client.dto.enums.RpcCredentialTypeEnum`  
**说明**: RPC 接口使用的凭证类型枚举，用于 Dubbo 服务调用

| 枚举值 | 代码 | 说明 | 是否为第三方 |
|--------|------|------|------------|
| USERNAME | 1 | 用户名 | 否 |
| PHONE | 2 | 手机号 | 否 |
| EMAIL | 3 | 邮箱 | 否 |
| WECHAT | 4 | 微信 | 是 |
| GOOGLE | 5 | Google | 是 |
| OTHER | 6 | 其他三方 | 是 |

**方法**: `fromCode(Integer code)`, `isThirdParty()`

## App 层枚举

### CredentialTypeEnum

**包路径**: `com.yggdrasil.labs.app.auth.dto.enums.CredentialTypeEnum`  
**说明**: App 层凭证类型枚举，对应 Domain 层的 `CredentialType`

| 枚举值 | 代码 | 说明 | 是否为第三方 |
|--------|------|------|------------|
| USERNAME | 1 | 用户名 | 否 |
| PHONE | 2 | 手机号 | 否 |
| EMAIL | 3 | 邮箱 | 否 |
| WECHAT | 4 | 微信 | 是 |
| GOOGLE | 5 | Google | 是 |
| OTHER | 6 | 其他三方 | 是 |

**方法**: `fromCode(Integer code)`, `isThirdParty()`

### LoginTypeEnum

**包路径**: `com.yggdrasil.labs.app.auth.dto.enums.LoginTypeEnum`  
**说明**: 登录类型枚举，对应 Domain 层的 `LoginType`

| 枚举值 | 代码 | 说明 |
|--------|------|------|
| PASSWORD | 1 | 密码登录 |
| WECHAT | 2 | 微信登录 |
| GOOGLE | 3 | Google登录 |
| TOKEN | 4 | 令牌登录 |
| OTHER | 5 | 其他 |

**方法**: `fromCode(Integer code)`

### TokenTypeEnum

**包路径**: `com.yggdrasil.labs.app.auth.dto.enums.TokenTypeEnum`  
**说明**: Token 类型枚举，对应 Domain 层的 `TokenType`

| 枚举值 | 代码 | 说明 |
|--------|------|------|
| ACCESS | 1 | 访问令牌 |
| REFRESH | 2 | 刷新令牌 |

**方法**: `fromCode(Integer code)`

### DeviceTypeEnum

**包路径**: `com.yggdrasil.labs.app.auth.dto.enums.DeviceTypeEnum`  
**说明**: 设备类型枚举

| 枚举值 | 代码 | 说明 |
|--------|------|------|
| WEB | "WEB" | Web设备 |
| MOBILE | "MOBILE" | 移动设备 |
| API | "API" | API调用 |

**注意**: 使用 `String` 类型作为代码  
**方法**: `fromCode(String code)`

### UserStatusEnum

**包路径**: `com.yggdrasil.labs.app.auth.dto.enums.UserStatusEnum`  
**说明**: 用户状态枚举，对应 Domain 层的 `UserStatus`

| 枚举值 | 代码 | 说明 | 是否可用 |
|--------|------|------|---------|
| NORMAL | 1 | 正常 | 是 |
| LOCKED | 2 | 锁定 | 否 |
| DISABLED | 3 | 禁用 | 否 |
| EXPIRED | 4 | 过期 | 否 |

**方法**: `fromCode(Integer code)`, `isAvailable()`, `isLocked()`

### AuthErrorCode

**包路径**: `com.yggdrasil.labs.app.auth.dto.enums.AuthErrorCode`  
**说明**: 认证服务错误码枚举

| 错误码 | 错误消息 | 说明 |
|--------|----------|------|
| USER_NOT_FOUND | 用户不存在 | 用户不存在 |
| CREDENTIAL_NOT_FOUND | 凭证不存在 | 凭证不存在 |
| CREDENTIAL_ALREADY_EXISTS | 凭证已存在 | 凭证已存在 |
| CANNOT_DELETE_PRIMARY_CREDENTIAL | 不能删除主凭证 | 不能删除主凭证 |
| TOKEN_NOT_FOUND | Token不存在 | Token不存在 |
| ACCOUNT_UNAVAILABLE | 账户不可用 | 账户不可用 |
| PASSWORD_NOT_SET | 密码未设置 | 密码未设置 |
| PASSWORD_INCORRECT | 密码错误 | 密码错误 |
| LOGIN_NOT_IMPLEMENTED | 登录功能待实现 | 登录功能待实现 |
| REFRESH_TOKEN_NOT_IMPLEMENTED | Token 刷新功能待实现 | Token 刷新功能待实现 |
| VERIFY_TOKEN_NOT_IMPLEMENTED | Token 验证功能待实现 | Token 验证功能待实现 |
| INVALID_CREDENTIAL_TYPE | 无效的凭证类型 | 无效的凭证类型 |
| PROVIDER_REQUIRED | OAuth 场景必须提供 provider | OAuth 场景必须提供 provider |

**注意**: 使用 `String` 类型作为错误码

## Domain 层枚举

### CredentialType

**包路径**: `com.yggdrasil.labs.domain.auth.model.enums.CredentialType`  
**说明**: 领域层凭证类型枚举，与 App 层的 `CredentialTypeEnum` 对应

| 枚举值 | 代码 | 说明 | 是否为第三方 |
|--------|------|------|------------|
| USERNAME | 1 | 用户名 | 否 |
| PHONE | 2 | 手机号 | 否 |
| EMAIL | 3 | 邮箱 | 否 |
| OAUTH | 4 | OAuth | 是 |

**注意**: Domain 层将第三方登录统一为 `OAUTH`，而 App 层和 Client 层细分为 `WECHAT`、`GOOGLE`、`OTHER`  
**方法**: `fromCode(Integer code)`, `isThirdParty()`

### LoginType

**包路径**: `com.yggdrasil.labs.domain.auth.model.enums.LoginType`  
**说明**: 领域层登录类型枚举，与 App 层的 `LoginTypeEnum` 对应

| 枚举值 | 代码 | 说明 |
|--------|------|------|
| PASSWORD | 1 | 密码登录 |
| WECHAT | 2 | 微信登录 |
| GOOGLE | 3 | Google登录 |
| TOKEN | 4 | 令牌登录 |
| OTHER | 5 | 其他 |

**方法**: `fromCode(Integer code)`

### TokenType

**包路径**: `com.yggdrasil.labs.domain.auth.model.enums.TokenType`  
**说明**: 领域层 Token 类型枚举，与 App 层的 `TokenTypeEnum` 对应

| 枚举值 | 代码 | 说明 |
|--------|------|------|
| ACCESS | 1 | 访问令牌 |
| REFRESH | 2 | 刷新令牌 |

**方法**: `fromCode(Integer code)`

### UserStatus

**包路径**: `com.yggdrasil.labs.domain.auth.model.enums.UserStatus`  
**说明**: 领域层用户状态枚举，与 App 层的 `UserStatusEnum` 对应

| 枚举值 | 代码 | 说明 | 是否可用 |
|--------|------|------|---------|
| NORMAL | 1 | 正常 | 是 |
| LOCKED | 2 | 锁定 | 否 |
| DISABLED | 3 | 禁用 | 否 |
| EXPIRED | 4 | 过期 | 否 |

**方法**: `fromCode(Integer code)`, `isAvailable()`, `isLocked()`

### LoginStatus

**包路径**: `com.yggdrasil.labs.domain.auth.model.enums.LoginStatus`  
**说明**: 登录状态枚举，用于记录登录操作的结果

| 枚举值 | 代码 | 说明 |
|--------|------|------|
| FAILED | 0 | 失败 |
| SUCCESS | 1 | 成功 |

**方法**: `fromCode(Integer code)`, `isSuccess()`

### PasswordStatus

**包路径**: `com.yggdrasil.labs.domain.auth.model.enums.PasswordStatus`  
**说明**: 密码状态枚举，用于标识密码的当前状态

| 枚举值 | 代码 | 说明 |
|--------|------|------|
| VALID | 1 | 有效 |
| EXPIRED | 2 | 已过期 |
| NEED_RESET | 3 | 需重置 |
| TEMPORARY | 4 | 临时密码 |

**方法**: `fromCode(Integer code)`

### PasswordAlgo

**包路径**: `com.yggdrasil.labs.domain.auth.model.enums.PasswordAlgo`  
**说明**: 密码加密算法枚举，用于标识密码使用的加密算法

| 枚举值 | 代码 | 说明 |
|--------|------|------|
| BCRYPT | 1 | BCrypt 算法 |
| ARGON2ID | 2 | Argon2id 算法 |
| PBKDF2 | 3 | PBKDF2 算法 |

**方法**: `fromCode(Integer code)`

### MfaType

**包路径**: `com.yggdrasil.labs.domain.auth.model.enums.MfaType`  
**说明**: 多因素认证（MFA）类型枚举

| 枚举值 | 代码 | 说明 |
|--------|------|------|
| TOTP | 1 | TOTP（时间-based一次性密码） |
| SMS | 2 | 短信验证码 |
| EMAIL | 3 | 邮箱验证码 |
| U2F | 4 | U2F |

**方法**: `fromCode(Integer code)`

### MfaFactorStatus

**包路径**: `com.yggdrasil.labs.domain.auth.model.enums.MfaFactorStatus`  
**说明**: MFA 因子状态枚举，用于标识 MFA 因子的启用/禁用状态

| 枚举值 | 代码 | 说明 |
|--------|------|------|
| ENABLED | 1 | 启用 |
| DISABLED | 2 | 禁用 |

**方法**: `fromCode(Integer code)`

## 枚举映射关系

### 凭证类型映射

| Client层 (RpcCredentialTypeEnum) | App层 (CredentialTypeEnum) | Domain层 (CredentialType) |
|----------------------------------|---------------------------|---------------------------|
| USERNAME (1) | USERNAME (1) | USERNAME (1) |
| PHONE (2) | PHONE (2) | PHONE (2) |
| EMAIL (3) | EMAIL (3) | EMAIL (3) |
| WECHAT (4) | WECHAT (4) | OAUTH (4) |
| GOOGLE (5) | GOOGLE (5) | OAUTH (4) |
| OTHER (6) | OTHER (6) | OAUTH (4) |

**说明**: Domain 层将第三方登录（WECHAT、GOOGLE、OTHER）统一映射为 `OAUTH`

### 登录类型映射

| App层 (LoginTypeEnum) | Domain层 (LoginType) |
|---------------------|---------------------|
| PASSWORD (1) | PASSWORD (1) |
| WECHAT (2) | WECHAT (2) |
| GOOGLE (3) | GOOGLE (3) |
| TOKEN (4) | TOKEN (4) |
| OTHER (5) | OTHER (5) |

**说明**: App 层和 Domain 层完全一致，一一对应

### Token 类型映射

| App层 (TokenTypeEnum) | Domain层 (TokenType) |
|----------------------|---------------------|
| ACCESS (1) | ACCESS (1) |
| REFRESH (2) | REFRESH (2) |

**说明**: App 层和 Domain 层完全一致，一一对应

### 用户状态映射

| App层 (UserStatusEnum) | Domain层 (UserStatus) |
|-----------------------|---------------------|
| NORMAL (1) | NORMAL (1) |
| LOCKED (2) | LOCKED (2) |
| DISABLED (3) | DISABLED (3) |
| EXPIRED (4) | EXPIRED (4) |

**说明**: App 层和 Domain 层完全一致，一一对应

## 枚举汇总表

### 凭证相关枚举

| 层次 | 枚举名称 | 代码类型 | 枚举值数量 | 主要用途 |
|------|---------|---------|-----------|---------|
| Client | RpcCredentialTypeEnum | Integer | 6 | RPC 接口凭证类型 |
| App | CredentialTypeEnum | Integer | 6 | App 层凭证类型 |
| Domain | CredentialType | Integer | 4 | 领域层凭证类型 |

### 登录相关枚举

| 层次 | 枚举名称 | 代码类型 | 枚举值数量 | 主要用途 |
|------|---------|---------|-----------|---------|
| App | LoginTypeEnum | Integer | 5 | App 层登录类型 |
| Domain | LoginType | Integer | 5 | 领域层登录类型 |
| Domain | LoginStatus | Integer | 2 | 登录状态 |

### Token 相关枚举

| 层次 | 枚举名称 | 代码类型 | 枚举值数量 | 主要用途 |
|------|---------|---------|-----------|---------|
| App | TokenTypeEnum | Integer | 2 | App 层 Token 类型 |
| Domain | TokenType | Integer | 2 | 领域层 Token 类型 |

### 用户状态枚举

| 层次 | 枚举名称 | 代码类型 | 枚举值数量 | 主要用途 |
|------|---------|---------|-----------|---------|
| App | UserStatusEnum | Integer | 4 | App 层用户状态 |
| Domain | UserStatus | Integer | 4 | 领域层用户状态 |

### 密码相关枚举

| 层次 | 枚举名称 | 代码类型 | 枚举值数量 | 主要用途 |
|------|---------|---------|-----------|---------|
| Domain | PasswordStatus | Integer | 4 | 密码状态 |
| Domain | PasswordAlgo | Integer | 3 | 密码加密算法 |

### MFA 相关枚举

| 层次 | 枚举名称 | 代码类型 | 枚举值数量 | 主要用途 |
|------|---------|---------|-----------|---------|
| Domain | MfaType | Integer | 4 | MFA 类型 |
| Domain | MfaFactorStatus | Integer | 2 | MFA 因子状态 |

### 其他枚举

| 层次 | 枚举名称 | 代码类型 | 枚举值数量 | 主要用途 |
|------|---------|---------|-----------|---------|
| App | DeviceTypeEnum | String | 3 | 设备类型 |
| App | AuthErrorCode | String | 13 | 错误码 |
