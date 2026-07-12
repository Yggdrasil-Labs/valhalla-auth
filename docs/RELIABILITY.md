# RELIABILITY.md

## 可靠性概述

valhalla-auth 作为认证服务，是所有需要身份验证的请求的关键路径。服务不可用将阻断所有登录和 Token 验证流程。

## 可用性目标

| 指标 | 目标 | 备注 |
|------|------|------|
| 服务可用性 | 99.9%（月度） | 认证服务属于 Tier-1 关键路径 |
| 登录响应时间 | P99 < 500ms | 包含密码验证和 Token 签发 |
| Token 验证响应时间 | P99 < 100ms | Redis 缓存命中场景 |
| RPC 调用超时 | < 3s | initializeUser 等同步调用 |

## 健康检查

- Spring Boot Actuator 暴露 `/actuator/health` 和 `/actuator/info`
- Docker HEALTHCHECK：每 30s 检查一次，启动宽限期 60s，3 次失败判定不健康
- `show-details: when-authorized`：敏感健康详情需授权访问

## 依赖可靠性

### MySQL 8.4

- 主要持久化存储：凭证、密码、MFA 因子
- 故障影响：新用户初始化失败、凭证查询失败
- 缓解：连接池配置、超时设置、慢查询监控

### Redis（规划组件，当前未集成）

- Token 元数据缓存、MFA 尝试计数、登录失败计数
- 故障影响：Token 验证退化为数据库查询、暴力破解防护失效
- 缓解：Redis 不可用时降级策略（TODO）

### Nacos

- 配置中心（`valhalla-auth.yaml`）+ 服务注册（Dubbo）
- 故障影响：配置无法热更新、RPC 服务发现失败
- 缓解：本地配置缓存、Nacos 重连机制、Dubbo 配置放 bootstrap（不受 Nacos 刷新影响）

## 容错设计

### 配置隔离

- Dubbo 配置放在 `bootstrap-dubbo.yaml`，不受 Nacos 配置刷新影响
- `use-as-config-center: false` + `use-as-metadata-center: false` 明确禁止 Nacos 干预 Dubbo 注册
- 环境变量覆盖：`DUBBO_REGISTRY_ADDRESS`、`DUBBO_NAMESPACE`、`DUBBO_GROUP`

### 事务保护

- 写操作 Executor 使用 `@Transactional(rollbackFor = Exception.class)`
- 跨服务调用（RPC）不参与本地事务，依赖最终一致性

### 数据完整性

- 凭证唯一约束：`uk_credential (credential_type, credential_value, deleted_at)`
- 软删除机制：`deleted_at` 字段避免物理删除导致的审计信息丢失
- 密码表以 `user_id` 为主键，保证一对一关系

## JVM 资源配置

- 容器默认：`-Xms128m -Xmx256m -XX:+UseSerialGC -XX:MaxRAM=512m`
- 适用于低流量开发/测试环境，生产环境需调整为 G1GC + 更大堆

## 日志策略

- 应用日志：`logs/valhalla-auth/info.log`
- 错误日志：`logs/valhalla-auth/error.log`
- SQL 日志：`logs/valhalla-auth/sql.log`（开发环境 DEBUG 级别）
- 访问日志：`logs/valhalla-auth/access.log`
- Nacos/Dubbo 框架日志级别降为 WARN，减少噪音

## 部署可靠性

- Docker 多阶段构建：构建阶段与运行阶段分离，减小攻击面
- 非 root 用户运行（`app:app`）
- 多架构支持：使用 `eclipse-temurin:17-jre-jammy`（非 Alpine，避免 arm64 manifest 缺失问题）
- BuildKit 缓存挂载加速 CI 构建

## 已知可靠性风险

| 风险 | 影响 | 状态 |
|------|------|------|
| Redis 不可用时无降级策略 | Token 验证和暴力破解防护失效 | 待设计 |
| 单实例部署无高可用 | 服务重启期间认证中断 | 待多实例部署方案 |
| 无熔断/限流机制 | 突发流量可能压垮服务 | 待引入 Sentinel/Resilience4j |
| 数据库连接池未显式配置 | 依赖框架默认值 | 待 Nacos 配置 |
