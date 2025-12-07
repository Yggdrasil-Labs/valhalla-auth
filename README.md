# valhalla-auth

> 英灵殿 - 认证服务（核心服务）

## 项目简介

Valhalla（英灵殿）是 Vanaheim 平台的**认证服务**，专注于用户身份认证，负责用户登录、登出、JWT Token 生成与验证等核心功能。

**职责边界**：

- ✅ **认证服务（valhalla-auth）**: 负责用户身份认证（Authentication）

- ✅ **用户服务（valhalla-user）**: 负责用户信息管理

- ✅ **网关服务（bifrost-gateway）**: 负责权限鉴权（Authorization）

## 技术栈

- **基础框架**: mimir-boot (Spring Boot 3.3.13 + Java 17)

- **架构模式**: Cola5.0 DDD 分层架构

- **服务注册**: Nacos + Dubbo

- **数据库**: MySQL 8.4

- **缓存**: Redis（Token 缓存）

- **认证**: JWT + Spring Security

## 核心功能

认证服务专注于**身份认证（Authentication）**，不涉及权限验证（Authorization）：

### 认证功能

- **账户密码登录**: 用户名/密码认证

- **JWT Token 生成**: 生成访问令牌（Access Token）和刷新令牌（Refresh Token）

- **Token 验证**: 验证 Token 有效性、签名、过期时间

- **Token 刷新**: 使用 Refresh Token 刷新 Access Token

- **登出功能**: 注销登录，撤销 Token

- **微信扫码登录**: 后续实现

### 账户安全

- **账户锁定**: 登录失败次数超限自动锁定

- **账户状态**: 管理账户启用/禁用状态

- **登录日志**: 记录登录历史（成功/失败）

**注意**: 

- ❌ **不负责权限验证**：权限验证由网关服务（bifrost-gateway）负责

- ❌ **不管理用户信息**：用户信息由用户服务（valhalla-user）管理
