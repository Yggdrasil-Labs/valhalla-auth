# Changelog

## [1.2.2](https://github.com/Yggdrasil-Labs/valhalla-auth/compare/v1.2.1...v1.2.2) (2026-01-31)


### 🐛 Bug Fixes

* **pom:** update mainClass path in pom.xml to reflect new package structure ([5ef0784](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/5ef07841c1d60e8b7992dddae0ab47918cc31aa1))


### 👷 Continuous Integration

* **release:** 增强 GitHub Actions 工作流程以更新 pom.xml 版本并为 GitHub Packages 部署配置 Maven ([ec6f982](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/ec6f9829d067b54e79f050ea606e2d7bd9b47849))
* **release:** 添加Maven Central配置以防止 Maven 插件错误，并更新构建配置以禁用Maven Central发布 ([7566abf](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/7566abfb51565dfdcd5a4cc25e228a48deba0cc4))
* **release:** 配置 GitHub Packages 发布和更新 pom.xml 以允许客户端模块发布 ([e541304](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/e54130499162251379f50da09c7fdea1ab9de9d6))


### 🔧 Miscellaneous Chores

* bump version to 1.2.2-SNAPSHOT for next development cycle ([a06b94b](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/a06b94bb415855f1f6d413d48746d2c096c12fb0))

## [1.2.1](https://github.com/Yggdrasil-Labs/valhalla-auth/compare/v1.2.0...v1.2.1) (2026-01-26)


### 👷 Continuous Integration

* **release:** 添加构建和打包客户端模块的步骤，并优化 POM 文件扁平化过程 ([ca0af6a](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/ca0af6ab62d9a0fe4335601d755691edffa719cf))
* **release:** 重构 GitHub Actions 工作流程以改进条件检查和 settings.xml 生成 ([0c39d33](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/0c39d33a8b334e02e9301ef91d39c26b76170be0))


### 🔧 Miscellaneous Chores

* bump version to 1.2.1-SNAPSHOT for next development cycle ([9a96102](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/9a96102f7c7e6336dd0694fdff1be33bb32b9772))

## [1.2.0](https://github.com/Yggdrasil-Labs/valhalla-auth/compare/v1.1.0...v1.2.0) (2026-01-26)


### ✨ Features

* 更新构建脚本以支持版本信息和本地构建说明 ([2302759](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/2302759f9ecf4f9c695dbfa5ba256b72df257c90))


### 👷 Continuous Integration

* **deps:** bump actions/checkout from 6.0.1 to 6.0.2 ([e61bae2](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/e61bae24bb212f4a926d3272c27184fef57f9fc3))
* **release:** 添加 central-publishing-maven-plugin 配置以跳过 Maven Central 发布 ([0c882c7](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/0c882c735f576f8ca17cc8314598e9536c33182a))
* **release:** 跳过发布到maven central ([fea20c3](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/fea20c33fb4af668773a217562680ea97a60d2aa))


### 🔧 Miscellaneous Chores

* bump version to 1.1.1-SNAPSHOT for next development cycle ([e6b84c0](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/e6b84c000db90fc104082b57e62e74736bfbb37b))
* **deps-dev:** bump com.diffplug.spotless:spotless-maven-plugin ([173200c](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/173200cc710e8e2ec062773a9704888eb9a9437d))
* 升级mimir-boot 到 2.0.0 ([131552c](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/131552cdf172a2fe41992d3e63870d76bf54e0a9))
* 将依赖项更新到版本2.0.2 ([b1b7bd5](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/b1b7bd5e7084fa7ac7e188b4b8c2449c7d278373))
* 更新 Dockerfile 和 GitHub Actions 工作流以支持多架构构建和版本解析 ([a3f8c6f](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/a3f8c6fa1f123d587a323b06d683654047358b9d))
* 更新 mimir-boot 版本至 2.0.3 并增强central-publishing-maven-plugin 配置 ([f6a4faf](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/f6a4fafa787f8a2ef1983f92f194fe4e4554f27b))
* 更新构建脚本以优化构建流程和文档 ([a87eaf2](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/a87eaf21a64e009c488e20167553d99505a5d872))

## [1.1.0](https://github.com/Yggdrasil-Labs/valhalla-auth/compare/v1.0.0...v1.1.0) (2026-01-15)


### ✨ Features

* 模块与目录重名命 ([1e1cb25](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/1e1cb250bb71ee15ef66ded5fc1757fff2daefa9))


### 👷 Continuous Integration

* **release:** client已重命名 ([c5b3694](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/c5b3694154be96eaf2366d8538c4cebca3f31929))


### 🔧 Miscellaneous Chores

* bump version to 1.0.1-SNAPSHOT for next development cycle ([bf66945](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/bf669454dd4730b7cd4e33538f1ce44fe51e7dfb))
* 避免changelog错误 ([0ec8eaf](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/0ec8eaf997bd071116ed800a07c3eaebd08323ef))

## 1.0.0 (2026-01-14)


### ✨ Features

* client层实现对外契约 ([65d02fe](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/65d02fe46d9ba37876986ac1d57771762a16cead))
* infra实现仓储接口与转换器 ([4d1b594](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/4d1b594b1fb7c59315d68c13fb281e04ce88122f))
* 完成认证服务整体 ([75db7f3](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/75db7f3c284929c3bd69fd9e53dcd27f3f66704b))
* 引入用户认证相关的应用服务和RPC接口，重构适配层依赖关系 ([d1ba8eb](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/d1ba8eba2131b3921770340060acce63f34974a7))
* 支持容器化构建 ([9f83261](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/9f8326198408212158f32d9c111612f69e45f474))
* 新增DO ([3d1097e](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/3d1097e4ab6f14a983586f32b91b82e63eff6e19))
* 新增数据库设计 ([cb2c2bf](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/cb2c2bf337551f4c33b81ef4c37ec0e29fb2e1a9))
* 新增领域实体与仓储接口 ([5398734](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/5398734ec328cbddb3362473a245f038c7ee05a6))
* 更新发布工作流以支持GitHub Packages，添加Maven配置步骤 ([7955e79](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/7955e795206489a0adf620570b069249744c0f58))
* 添加发布客户端包的工作流，支持版本更新和部署到GitHub Packages ([b72e63f](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/b72e63f827133b1fd4372d8df26b634d23da39a9))
* 添加新的认证错误代码以增强错误处理机制 ([d39e1cc](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/d39e1cc5c5a0a69651fa8a821d7a2e12fe118329))
* 添加枚举值统一说明文档和初始化用户设计说明文档，详细描述各层次的枚举类型及其用途 ([526eb20](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/526eb20154a9c071180ef8758f470403d9148abf))
* 添加用户初始化 Dubbo RPC 服务 ([1435e27](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/1435e27b128bb8ee5becc2e2f77031c6491aa6a4))


### 🐛 Bug Fixes

* 使用mimir-boot发布版 ([a7c2e54](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/a7c2e5473a8ae40b8001ab2084c25f9f66331e06))
* 禁用dubbo的配置中心、元数据中心（启动时控制台会有warn、error，但是不影响） ([486265e](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/486265e1d4654b0dbc9fa0d524318ace6d1ca8b9))
* 统一镜像的组织 ([59682cb](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/59682cbb3998175c3dd5d9e91e1772d271b2b62c))


### ♻️ Code Refactoring

* 使用依赖注入替代单例模式，优化AuthRpcConverter和相关转换器的结构 ([7b65993](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/7b65993c941502eea803e3e05288de902288300d))
* 根据最新的表结构重构代码，移除不需要的代码 ([765609d](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/765609d5ebcdacb5cd0711264d8225dabe4e198a))
* 清理无关代码 ([d80829d](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/d80829db3a82b2e3ff9e16e71860e1462858c4ca))
* 移除不必要的Mappers实例，改为使用依赖注入的方式 ([4ec66e1](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/4ec66e1944c20ab761b378ab0949e051d83ca6e5))
* 移除客户领域相关的包和文档，精简代码结构 ([36fa330](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/36fa3307e902fed012cee37ed6bf8be24663a218))
* 移除暂不需要的实体、仓储和转换器，精简代码结构 ([87462ca](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/87462ca701f1be99111b1c130c075dfdea79b0b3))
* 避免使用var ([9c7129e](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/9c7129e5ad03f79a6cc8baa905d67f0d68ecf3c9))
* 重构用户认证相关表结构，移除不必要的表并引入密码和多因子认证表 ([049ad68](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/049ad6811b799e382b0a8a328bfad4f8bdf13be0))


### 👷 Continuous Integration

* **deps:** bump googleapis/release-please-action from 4.2.0 to 4.4.0 ([a5ab370](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/a5ab3703281546ddf5a9546908a2d21385830b69))
* **deps:** bump softprops/action-gh-release from 1 to 2 ([1042b73](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/1042b732ec4180ea373f0c954e872f08c6ec1a10))
* **release:** 美化Changelog，带上Icon ([55fcee3](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/55fcee31caa2832bd7f29bf6b13d16adfca7c045))
* 修改判断逻辑，避免误升级 ([bce1164](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/bce1164f5431f1667429f6ffa6def4eb4857e5b2))
* 修改自动发布相关流水线 ([562a978](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/562a978255ac7762c378bc7758b8067fe0145ea9))
* 同步midgard模板仓库的工作流优化 ([0be52c5](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/0be52c5ae24e244beb7db47181a31e109aa2b335))
* 版本从0.0.0开始 ([d9e67ed](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/d9e67ed4c9a14a3c293c651b3120a64d2e6bdbd7))


### 🔧 Miscellaneous Chores

* **deps:** bump org.apache.dubbo:dubbo-bom from 3.2.8 to 3.3.6 ([8c27599](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/8c275992e8b037ad052741fe5ee9f845d16e4447))
* dubbo相关依赖调整 ([3937782](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/39377823c56ba4ccde1f1204893d82238686d03d))
* update mimir-boot version to 1.4.1 in pom.xml ([e69f02e](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/e69f02e31fe33e60cd380e3a5f4ebb913bb1bab8))
* 初始化，清理模板内容 ([cb0dcd6](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/cb0dcd60e94ddf2b6f0bf2a794d6ea2cc5f9a214))
* 升级mimir-boot到1.4.2 ([72e2372](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/72e237256bc7eecf3d88429c91af7cbfa65b4dc3))
* 将dubbo配置单独放置，不需要nacos的动态刷新 ([ce47a3f](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/ce47a3f6719b84c7628404f51a4956e079743d7b))


### 💄 Code Style

* code format ([bfe3026](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/bfe3026170cfce597b5d65d8cf7fa6b774eb0a4c))
