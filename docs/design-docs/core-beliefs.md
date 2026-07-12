# core-beliefs.md

## 工程信条

本文件记录 valhalla-auth 项目的核心工程原则。这些信条指导日常技术决策，非架构 RFC 不可更改。

### 1. 认证与用户职责分离

auth 服务只管"证明身份"，不存储用户画像信息。`userId` 是跨服务关联的唯一纽带。这确保：
- 认证逻辑独立演进，不被用户业务拖累
- 安全敏感数据（密码、MFA 密钥）集中管理
- 未来可独立扩缩容

### 2. COLA 分层严格执行

依赖只能由外层指向内层，domain 层零框架依赖：
- domain 只依赖 COLA 组件（Entity + Exception）+ 基础工具类（Lombok, commons-lang3），不引入 Spring
- infrastructure 实现 domain 定义的 Repository 接口
- adapter 通过 ApplicationService 接口调用 app 层，不跳层

### 3. 命令查询分离（CQRS）

写操作走 Executor，读操作走 Query：
- 每个写操作对应一个独立的 Executor 类（如 `LoginExecutor`、`InitializeUserExecutor`）
- 查询统一收口到 `AuthQuery` 类
- ApplicationService 是编排入口，不包含业务逻辑

### 4. 契约即边界

`valhalla-auth-client` 是对外发布的独立 JAR：
- 只包含 RPC 接口定义（`AuthRpcFacade`）和 DTO
- 不依赖任何内部模块
- 版本变更即对外契约变更，需谨慎评估兼容性

### 5. 配置分层不混淆

- `bootstrap-dubbo.yaml`：Dubbo 配置，不受 Nacos 刷新影响
- `application-{profile}.yml`：环境相关配置，通过 Nacos 热更新
- 环境变量覆盖：生产环境地址通过 `DUBBO_REGISTRY_ADDRESS` 等变量注入

### 6. 安全默认

- 密码永远单向哈希，从不存储明文
- 敏感字段（MFA 密钥、备用码）加密存储
- Token 设计为可吊销，不依赖 JWT 自然过期
- 软删除保留审计痕迹

### 7. 格式即法律

- Spotless + Google AOSP 4 空格风格
- CI 阶段 `spotless:check` 必须通过
- 导入顺序：`java, javax, jakarta, org, com, cn`
- 文件末尾保留空行，删除未使用导入

### 8. 数据库迁移可追溯

- 所有 DDL 通过 `db/migration/V{version}__描述.sql` 管理
- 禁止直接修改 `db/schema/` 文件后手动同步
- 每次迁移不可变，新变更新建文件
