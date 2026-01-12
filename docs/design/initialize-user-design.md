# 初始化用户设计说明

## 概述

本文档说明了 `initializeUser` 接口的架构设计、实现原理和关键设计决策。

## 架构设计

### 分层架构

| 层次 | 组件 | 职责 |
|------|------|------|
| Client层 | RpcInitializeUserCmd, RpcUserInitializationCO | 定义 RPC 接口契约 |
| Adapter层 | AuthRpcFacadeImpl, AuthRpcConverter | RPC 接口实现和 DTO 转换 |
| App层 | AuthApplicationService, InitializeUserExecutor | 业务逻辑编排和执行 |
| Domain层 | AuthCredential, AuthPassword | 领域模型和业务规则 |
| Infrastructure层 | Repository, Converter | 数据持久化和转换 |

### 调用链路

1. 调用方服务 → AuthRpcFacadeImpl (Dubbo RPC)
2. AuthRpcFacadeImpl → AuthRpcConverter (DTO 转换)
3. AuthRpcConverter → AuthApplicationService
4. AuthApplicationService → InitializeUserExecutor
5. InitializeUserExecutor → Domain 模型和 Repository
6. Repository → 数据库

## 核心流程

### 执行步骤

1. **接收请求**: 接收 `RpcInitializeUserCmd`
2. **DTO 转换**: 转换为 `InitializeUserCmd`
3. **验证凭证类型**: 检查凭证类型是否有效
4. **检查凭证存在性**: 验证凭证是否已存在
5. **处理密码**:
   - 第三方登录：验证 provider 参数
   - 非第三方登录：生成或使用初始密码，加密存储
6. **创建凭证对象**: 根据类型创建 `AuthCredential`
7. **设置状态**: 设置验证状态和主凭证标识
8. **保存数据**: 保存凭证和密码到数据库
9. **组装返回结果**: 转换为 `RpcUserInitializationCO` 返回

### 密码处理逻辑

| 场景 | 处理方式 |
|------|---------|
| 第三方登录 (OAuth) | 不需要密码，必须提供 provider |
| 非第三方登录 + 提供初始密码 | 使用提供的密码，加密后存储 |
| 非第三方登录 + 未提供密码 | 自动生成默认密码，加密后存储 |

## 数据模型

### 领域模型

**AuthCredential**
- 字段: id, userId, type, value, provider, verified, isPrimary
- 方法: create(), createThirdParty(), verify(), setAsPrimary()

**AuthPassword**
- 字段: id, userId, hash, forceChange
- 方法: createInitialPassword()

### 数据库表

**auth_credential**
- 主键: id
- 索引: user_id, (type, value) 唯一索引
- 字段: user_id, type, value, provider, verified, is_primary, created_at, updated_at

**auth_password**
- 主键: id
- 索引: user_id
- 字段: user_id, hash, force_change, created_at, updated_at

### DTO 转换链路

| 层次 | 请求 DTO | 响应 DTO |
|------|---------|---------|
| Client层 | RpcInitializeUserCmd | RpcUserInitializationCO |
| App层 | InitializeUserCmd | UserInitializationCO |
| Domain层 | AuthCredential | AuthPassword |

## 关键设计决策

### 1. 分层转换设计

**设计理由**:
- **解耦**: Client 层和 App 层使用不同的 DTO，避免直接依赖
- **类型安全**: 每层使用独立的枚举类型，编译时检查类型错误
- **可维护性**: 转换逻辑集中在 Converter 中，易于修改

### 2. 密码处理策略

**设计理由**:
- **灵活性**: 支持自动生成和用户提供两种方式
- **安全性**: 所有密码都经过加密存储，不存储明文
- **用户体验**: 自动生成时返回明文密码，便于首次登录

### 3. 凭证类型扩展性

**设计理由**:
- **类型安全**: 使用枚举而非字符串，避免类型错误
- **扩展性**: 新增凭证类型只需添加枚举值和相应逻辑
- **业务区分**: 通过 `isThirdParty()` 方法区分处理逻辑

## 异常处理

### 错误码

| 错误码 | 说明 |
|--------|------|
| INVALID_CREDENTIAL_TYPE | 无效的凭证类型 |
| PROVIDER_REQUIRED | OAuth 场景必须提供 provider |
| CREDENTIAL_ALREADY_EXISTS | 凭证已存在 |

### 异常处理策略

- **业务异常**: 转换为错误码返回
- **系统异常**: 记录日志，返回系统错误
- **事务异常**: 自动回滚事务

## 事务管理

### 事务配置

- **传播行为**: `REQUIRED`（默认）
- **隔离级别**: `READ_COMMITTED`（默认）
- **回滚策略**: `rollbackFor = Exception.class`（所有异常都回滚）

### 事务范围

事务包含以下操作：
1. 验证凭证类型
2. 检查凭证存在性
3. 创建凭证对象
4. 保存凭证
5. 创建密码对象（如需要）
6. 保存密码（如需要）

## 性能考虑

### 数据库索引

| 表名 | 索引 | 用途 |
|------|------|------|
| auth_credential | user_id | 查询用户所有凭证 |
| auth_credential | (type, value) 唯一索引 | 防止重复创建，快速查找 |
| auth_password | user_id | 查询用户密码 |

### 优化建议

1. **缓存**: 可考虑缓存热点凭证数据
2. **批量操作**: 未来可扩展批量初始化接口

## 扩展性设计

### 扩展点

1. **凭证类型扩展**: 新增枚举值，实现 `isThirdParty()` 方法
2. **密码策略扩展**: 实现 `PasswordGenerator` 接口，配置密码策略
3. **验证流程扩展**: 扩展验证逻辑，支持多因素认证

### 未来扩展场景

1. **多因素认证 (MFA)**: 在初始化时可选创建 MFA 因子
2. **密码策略配置化**: 支持不同场景的密码策略
3. **批量初始化**: 支持批量创建用户凭证
4. **凭证验证流程**: 支持异步验证流程

## 安全设计

### 安全措施

| 措施 | 说明 |
|------|------|
| 密码加密 | 使用 BCrypt 算法加密存储，不存储明文 |
| 参数校验 | 严格校验参数类型、格式和业务规则 |
| 权限控制 | RPC 服务隔离，不暴露 HTTP 接口 |
| 数据验证 | 检查凭证唯一性，防止重复创建 |

## 总结

该设计遵循以下原则：

1. **分层架构**: 清晰的层次划分，职责明确
2. **类型安全**: 使用强类型和枚举，避免运行时错误
3. **可扩展性**: 预留扩展点，便于未来功能扩展
4. **安全性**: 密码加密存储，参数严格校验
5. **可维护性**: 代码结构清晰，易于理解和修改
