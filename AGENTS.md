# AGENTS.md

本文件是智能体的唯一入口，保持为"地图而不是手册"。

## 项目概述

valhalla-auth 是英灵殿（Valhalla）平台的认证微服务，负责用户身份验证、JWT Token 生命周期管理和账户安全策略。服务面向平台内所有需要身份认证的客户端（Web、Mobile、WAP）以及内部微服务（通过 Dubbo RPC）。技术选型：Java 17 + Spring Boot 3.3（mimir-boot 2.1.0）+ COLA 5.0 DDD 分层架构 + Nacos 配置中心 + MySQL 8.4 + Redis + Apache Dubbo 3.3 RPC + MapStruct。

## 全局规范

1. 智能体优先遵循项目规范（`AGENTS.md`、`ARCHITECTURE.md`、`docs/design-docs/`）。项目约束 > 智能体全局约束。
2. Git Conventional Commits，message 中文。格式：`<type>(<scope>): <中文描述>`。
3. 文档与代码冲突时以代码为准并回写文档。

## 导航

### A. 长期约束（只读，修改需架构 RFC）

- 系统边界与依赖方向：[`ARCHITECTURE.md`](./ARCHITECTURE.md)
- 工程信条：[`docs/design-docs/core-beliefs.md`](./docs/design-docs/core-beliefs.md)
- 业务领域划分：[`docs/DOMAINS.md`](./docs/DOMAINS.md)
- 安全策略：[`docs/SECURITY.md`](./docs/SECURITY.md)
- 可靠性标准：[`docs/RELIABILITY.md`](./docs/RELIABILITY.md)

### B. 流转文档

- 活跃版本：[`docs/active/index.md`](./docs/active/index.md)
- 版本归档：[`docs/archive/index.md`](./docs/archive/index.md)
- 技术债：[`docs/active/tech-debt-tracker.md`](./docs/active/tech-debt-tracker.md)
- 设计决策：[`docs/design-docs/index.md`](./docs/design-docs/index.md)

### C. 参考与产物

- 产品思维：[`docs/PRODUCT_SENSE.md`](./docs/PRODUCT_SENSE.md)

## 决策地图

| 改什么 | 去哪里 |
|--------|--------|
| 新增 REST API 端点 | `valhalla-auth-adapter/src/main/java/{…}/adapter/web/controller/` |
| 新增 Dubbo RPC 方法 | `valhalla-auth-client/` 定义接口 + `valhalla-auth-adapter/{…}/rpc/provider/` 实现 |
| 新增业务命令 | `valhalla-auth-app/{…}/executor/` 新建 Executor |
| 新增查询 | `valhalla-auth-app/{…}/query/AuthQuery.java` 添加方法 |
| 修改领域模型 | `valhalla-auth-domain/{…}/domain/auth/model/` |
| 调整持久化逻辑 | `valhalla-auth-infrastructure/{…}/persistence/` |
| 变更对外契约 | `valhalla-auth-client/{…}/client/` |
| 新增数据库表/字段 | `db/migration/V{n}__描述.sql` |
| 修改服务配置 | `valhalla-auth-start/src/main/resources/application*.yml`（本地），Nacos 配置中心（远程） |
| 修改 Dubbo 配置 | `valhalla-auth-start/src/main/resources/bootstrap-dubbo.yaml`（不可被 Nacos 刷新） |
| 新增 mimir-boot Starter 依赖 | `valhalla-auth-start/pom.xml` |

## 开发命令

```bash
# 本地构建（dev profile，自动格式化）
./mvnw clean package -Pdev

# 格式化代码
./mvnw spotless:apply

# CI 构建（格式检查 + 编译，跳过测试）
./mvnw -B clean package -Pci -DskipTests

# 运行测试
./mvnw test -Pprecheck

# 格式检查（不修改文件）
./mvnw spotless:check
```
