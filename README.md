# midgard-backend-template

> 中庭 - 基于 COLA 5.0 的 DDD 架构模板项目

## 项目简介

Midgard（中庭）是基于 COLA 5.0 DDD 架构的微服务模板项目，用于快速创建符合 DDD 最佳实践的后端服务。

## 技术栈

- **基础框架**: Spring Boot 3.3.13 + Java 17
- **架构模式**: COLA 5.0 DDD 分层架构
- **服务注册**: Nacos 2.x
- **服务通信**: OpenFeign（对外）+ Dubbo 3.x（内部）
- **数据库**: MySQL 8.4 + MyBatis-Plus（使用 @AutoMybatis 自动生成 Mapper）
- **对象转换**: MapStruct
- **缓存**: Redis
- **消息队列**: Kafka / RocketMQ（可选）

## 架构设计

### 分层架构

```
┌─────────────────────────────────────────┐
│  Start（启动层）                         │  ← 启动 + 配置
├─────────────────────────────────────────┤
│  Adapter（适配层）                       │  ← 协议适配（HTTP、RPC、MQ）
├─────────────────────────────────────────┤
│  Client（客户端层）                      │  ← 对外契约
├─────────────────────────────────────────┤
│  App（应用服务层）                       │  ← 业务编排
├─────────────────────────────────────────┤
│  Domain（领域层）                        │  ← 业务规则
├─────────────────────────────────────────┤
│  Infrastructure（基础设施层）            │  ← 技术实现
└─────────────────────────────────────────┘
```

### 核心原则

| 层 | 职责 | 依赖 | 原则 |
|---|------|------|------|
| **Client** | 对外契约（API、DTO） | COLA 基础类 | 只定义不实现 |
| **Adapter** | 协议适配（HTTP→Cmd/Query） | Client | 薄适配层，不含业务逻辑 |
| **App** | 业务编排（流程控制） | Client + Domain | 不放规则，只做编排 |
| **Domain** | 业务规则（Entity、Repository 接口） | 无 | 规则中心，不依赖外部框架 |
| **Infrastructure** | 技术实现（DO、Repository 实现） | Domain | 实现 Domain 接口（依赖倒置） |
| **Start** | 启动配置 | 所有层 | 只做启动 + 配置 |

### 依赖关系

```mermaid
flowchart TD
    Start[Start 启动层] -->|依赖| Adapter[Adapter 适配层]
    Start -->|依赖| App[App 应用层]
    Start -->|依赖| Infrastructure[Infrastructure 基础设施层]
    
    Adapter -->|依赖| Client[Client 客户端层]
    App -->|依赖| Client
    App -->|依赖| Domain[Domain 领域层]
    Infrastructure -->|依赖| Domain
    
    style Domain fill:#e8f5e9
    style Client fill:#e8f5e9
```

## 项目结构

### 各层子包说明

#### Client 层（`client`）

| 子包 | 用途 | 命名规范 |
|-----|------|---------|
| `api` | 业务接口 | `{Domain}Client` |
| `dto/cmd` | 命令对象（写） | `{Verb}{Domain}Cmd` |
| `dto/query` | 查询对象（读） | `{Verb}{Domain}Query` |
| `dto/co` | 客户对象（输出） | `{Domain}CO` |
| `dto/enums` | 枚举、错误码 | `{Name}Enum`、`ErrorCode` |

**动词映射表**：
- Command：`Create`、`Update`、`Modify`、`Add`、`Delete`、`Remove`
- Query：`Get`、`Query`、`List`、`Page`、`Check`

#### Adapter 层（`adapter`）

| 子包 | 用途 | 命名规范 |
|-----|------|---------|
| `web/controller` | REST 控制器 | `{Domain}Controller` |
| `web/request` | Web 请求对象 | `{Verb}{Domain}Request` |
| `web/convert` | 请求转换器 | `{Domain}WebConverter` |
| `rpc/provider` | RPC 服务提供者 | `{Domain}RpcProvider` |
| `mq/consumer` | MQ 消息消费者 | `{Domain}MqConsumer` |

#### App 层（`app`）

| 子包 | 用途 | 命名规范 |
|-----|------|---------|
| `{aggregate}` | 聚合根业务包 | 小写聚合名（如 `customer`） |
| `executor` | 命令/查询执行器 | `{Domain}{Action}CmdExe`、`{Domain}{Action}QryExe` |
| `convert` | Cmd→Entity 转换器 | `{Domain}Converter` |
| `assembler` | Entity→CO 组装器 | `{Domain}Assembler` |
| `listener` | 事件监听器 | `{Domain}EventListener` |

**Client 接口实现**：`{Domain}ClientImpl`

#### Domain 层（`domain`）

| 子包 | 用途 | 命名规范 |
|-----|------|---------|
| `{aggregate}/model` | 领域模型 | `{Domain}`（Entity）、`{Name}`（VO） |
| `{aggregate}/service` | 领域服务 | `{Domain}DomainService` |
| `{aggregate}/repository` | 仓储接口 | `{Domain}Repository` |
| `{aggregate}/event` | 领域事件 | `{Domain}{Action}Event`（过去时） |

#### Infrastructure 层（`infrastructure`）

| 子包 | 用途 | 命名规范 |
|-----|------|---------|
| `persistence/dataobject` | 数据库对象 | `{Domain}DO` + `@AutoMybatis` |
| `persistence/converter` | DO↔Entity 转换器 | `{Domain}Converter` |
| `persistence/impl` | Repository 实现 | `{Domain}RepositoryImpl` |
| `gateway` | 第三方服务调用 | `{External}GatewayImpl` |
| `config` | 技术配置 | `{Tech}Config` |

## 对象转换链路

```mermaid
flowchart LR
    A[HTTP Request<br/>JSON] -->|Jackson| B[Request DTO]
    B -->|WebConverter| C[Command/Query]
    C -->|Converter| D[Entity]
    D -->|Converter| E[DO]
    E -->|MyBatis-Plus| F[(Database)]
    
    F -->|MyBatis-Plus| E
    E -->|Converter| D
    D -->|Assembler| G[CO]
    G -->|Jackson| H[HTTP Response<br/>JSON]
    
    style A fill:#e3f2fd
    style H fill:#e3f2fd
    style D fill:#fff9c4
    style E fill:#f3e5f5
    style F fill:#e8f5e9
```

### 转换器职责

| 层 | 转换器 | 方向 | 工具 |
|---|--------|------|------|
| Adapter | `{Domain}WebConverter` | Request ↔ Cmd/Query | MapStruct |
| App | `{Domain}Converter` | Cmd → Entity | MapStruct |
| App | `{Domain}Assembler` | Entity → CO | MapStruct |
| Infrastructure | `{Domain}Converter` | Entity ↔ DO | MapStruct |

### 参数校验

1. **Client 层**：使用 JSR 303 注解（`@NotBlank`、`@Size`、`@Pattern`）
2. **Adapter 层**：使用 `@Validated` + `@Valid` 启用校验
3. **Domain 层**：在 Entity 的 `validate()` 方法中校验业务规则

## 快速开始

### 开发流程

```bash
# 1. Client 层 - 定义契约
├── CustomerClient 接口
├── CreateCustomerCmd（带校验注解）
├── ListCustomerQuery
└── CustomerCO

# 2. Domain 层 - 定义模型
├── Customer Entity（含 validate() 方法）
└── CustomerRepository 接口

# 3. Infrastructure 层 - 技术实现
├── CustomerDO（@AutoMybatis 自动生成 Mapper）
├── CustomerConverter（DO ↔ Entity）
└── CustomerRepositoryImpl

# 4. App 层 - 业务编排
├── CustomerConverter（Cmd → Entity）
├── CustomerAssembler（Entity → CO）
├── CustomerCreateCmdExe（@Transactional）
├── CustomerListQryExe
└── CustomerClientImpl

# 5. Adapter 层 - 协议适配
├── CreateCustomerRequest
├── CustomerWebConverter
└── CustomerController（@Validated）
```

## 常见问题

<details>
<summary><b>Q1: 为什么没有 Mapper 接口文件？</b></summary>

使用 `@AutoMybatis` 注解在编译期自动生成 Mapper 和 Service：

```java
@TableName("customer")
@AutoMybatis  // 自动生成 CustomerMapper 和 CustomerService
public class CustomerDO { }
```
</details>

<details>
<summary><b>Q2: Entity 与 DO 的区别？</b></summary>

- **Entity（领域实体）**：包含业务逻辑和行为方法
- **DO（数据对象）**：只包含数据字段，对应数据库表

通过 Converter 转换，保持两者独立。
</details>

<details>
<summary><b>Q3: Converter 与 Assembler 的区别？</b></summary>

- **Converter**：Cmd → Entity（写入方向）
- **Assembler**：Entity → CO（读取方向）
</details>

<details>
<summary><b>Q4: 如何调用第三方服务？</b></summary>

使用 Gateway 模式：
1. Domain 层定义 Gateway 接口（如 `PaymentGateway`）
2. Infrastructure 层实现接口（如 `PaymentGatewayImpl`）
</details>

## 最佳实践

### ✅ 关键原则

- **依赖倒置**：Domain 定义接口，Infrastructure 实现
- **单一职责**：各层职责清晰，避免越界
- **事务边界**：在 App 层 Executor 上使用 `@Transactional`
- **参数校验**：Client 层 JSR 303 + Domain 层业务规则
- **异常处理**：技术异常转换为领域异常

### 📚 详细文档

- 各包的 `package-info.java` 包含详细架构说明和代码示例
- `openspec/` 目录包含项目规范和设计文档

## 使用模板

```bash
# 1. 克隆项目
git clone <template-repo-url>

# 2. 修改包名
# 全局替换：com.yggdrasil.labs → com.your.company

# 3. 修改项目名
# pom.xml: midgard-backend-template → your-service-name

# 4. 配置环境
# start/src/main/resources/application.yml

# 5. 启动项目
./mvnw spring-boot:run
```

## 相关资源

- [COLA 架构](https://github.com/alibaba/COLA)
- [阿里巴巴 Java 开发手册](https://github.com/alibaba/p3c)
- [MapStruct 文档](https://mapstruct.org/)
- [MyBatis-Plus 文档](https://baomidou.com/)

## 贡献指南

遵循 [Conventional Commits](https://www.conventionalcommits.org/) 规范：

```
feat: 新增功能
fix: 修复 Bug
docs: 文档更新
refactor: 代码重构
```