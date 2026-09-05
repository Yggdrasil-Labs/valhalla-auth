# Changelog

## [1.3.0](https://github.com/Yggdrasil-Labs/valhalla-auth/compare/v1.2.2...v1.3.0) (2026-09-05)


### ✨ Features

* **adapter:** 扩展 AuthRpcFacade 支持 verifyToken RPC 调用 ([e5a23b7](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/e5a23b78a077d1e9ef3d68bd4eedbb8a904f7185))
* **auth:** 实现 LoginExecutor 密码验证与账号锁定逻辑 ([a637413](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/a6374134372332310ed05820b19f10b6bebda270))
* **auth:** 实现 VerifyToken/RefreshToken/Logout Executor 令牌操作 ([35fbdad](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/35fbdad94c5b67e758d438b882fbd6e8aca4baa2))
* **infra:** 实现 TokenServiceImpl Token 生命周期管理 ([8af9c31](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/8af9c318d5e80f24da452b2e9d8d22b26efc6211))
* **infra:** 引入 Redis/JWT 依赖、实现 JwtTokenProvider、定义 TokenService 接口 ([29d124e](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/29d124ea4287905fc524770f4b7b8c0892a9aa3f))


### 📝 Documentation

* **init:** 初始化文档体系 ([327751c](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/327751c8c019a0b4d07d1e64221cd62c4deeac46))
* **token-lifecycle:** 完成需求设计文档 ([603d3e0](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/603d3e092b2a7ea5bc7e14b770aa1a551fbab164))
* **token:** 更新 Token 生命周期方案——RT 轮换与重放检测 ([098bc8d](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/098bc8d65918d6b0ef4ba335d2284b40244f4a83))


### ✅ Tests

* **integration:** 新增 Token 生命周期集成测试 ([b45f193](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/b45f193cfd80682dbf6b95c2d89fad55eb478dc5))
* **integration:** 新增 Token 生命周期集成测试 ([1aeb6b3](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/1aeb6b3cd8ee5aaeb64b8ac14c0091615b3c7347))


### 👷 Continuous Integration

* **deps:** bump actions/checkout from 6.0.1 to 7.0.0 ([545bcc6](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/545bcc647cf9058d93a5dfc6222146472d9c1472))
* **deps:** bump actions/checkout from 6.0.1 to 7.0.0 ([170b846](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/170b8461d8b54aedd69fe7df0af7845ba1a6e12d))
* **deps:** bump actions/checkout from 7.0.0 to 7.0.1 ([#89](https://github.com/Yggdrasil-Labs/valhalla-auth/issues/89)) ([1ec2b73](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/1ec2b73f22703884fd12bdaf3ee0bbc7caf8c9ec))
* **deps:** bump actions/github-script from 8.0.0 to 9.0.0 ([ce9fc89](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/ce9fc89d914239b4d5a8750d2905a8804abf6fe6))
* **deps:** bump docker/build-push-action from 6.18.0 to 6.19.2 ([d734dee](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/d734dee42fda927bbfd5ce6467b8cb2b74a3bede))
* **deps:** bump docker/build-push-action from 6.19.2 to 7.0.0 ([fcf9d5e](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/fcf9d5eb916e707d81fdaf064561794d7bfa0612))
* **deps:** bump docker/build-push-action from 7.0.0 to 7.1.0 ([5eaef0b](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/5eaef0bfade857a3aaa32561eaca777ac18c1780))
* **deps:** bump docker/build-push-action from 7.1.0 to 7.2.0 ([#67](https://github.com/Yggdrasil-Labs/valhalla-auth/issues/67)) ([0e84214](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/0e84214dc44ff927f41afad09e55e308a36836ac))
* **deps:** bump docker/build-push-action from 7.2.0 to 7.3.0 ([#85](https://github.com/Yggdrasil-Labs/valhalla-auth/issues/85)) ([6204534](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/62045342279312a2addedfeef33232d0306c8fdd))
* **deps:** bump docker/login-action from 3.4.0 to 3.7.0 ([c90fe0c](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/c90fe0c723fb1e2da5e6036bea18a4868a638721))
* **deps:** bump docker/login-action from 3.7.0 to 4.1.0 ([bb16807](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/bb1680775f7cf978fca07c1967e319dea91685f4))
* **deps:** bump docker/login-action from 4.1.0 to 4.2.0 ([#65](https://github.com/Yggdrasil-Labs/valhalla-auth/issues/65)) ([3da753c](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/3da753cf0bd2213522d864182022e9dcd197b280))
* **deps:** bump docker/login-action from 4.2.0 to 4.6.0 ([#92](https://github.com/Yggdrasil-Labs/valhalla-auth/issues/92)) ([b411645](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/b4116455d1d326cb3afd3f9b42850dc77e2c9520))
* **deps:** bump docker/metadata-action from 5.7.0 to 6.0.0 ([4852ed3](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/4852ed3824260e9e5feae33d138703e781c330e8))
* **deps:** bump docker/metadata-action from 6.0.0 to 6.1.0 ([#66](https://github.com/Yggdrasil-Labs/valhalla-auth/issues/66)) ([1abb653](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/1abb653c64025b3130f6184dbb46d9189df4bf44))
* **deps:** bump docker/metadata-action from 6.1.0 to 6.2.0 ([43f5cc0](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/43f5cc0f7cd968d2de9c30bb4ea5e95365b403d3))
* **deps:** bump docker/metadata-action from 6.1.0 to 6.2.0 ([a66d589](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/a66d589fc8e79a3697cb6a506b691aefd5c6add8))
* **deps:** bump docker/setup-buildx-action from 3.10.0 to 4.0.0 ([1559058](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/15590588fa3bfa735c7477f41a8b0425748686ef))
* **deps:** bump docker/setup-buildx-action from 4.0.0 to 4.1.0 ([#64](https://github.com/Yggdrasil-Labs/valhalla-auth/issues/64)) ([b64ed58](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/b64ed58d6a3d36eab919f11de5a1f6c8c9925275))
* **deps:** bump docker/setup-buildx-action from 4.1.0 to 4.2.0 ([12cd489](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/12cd489426f2f6b3373c85cbb83aa7494518f7dd))
* **deps:** bump docker/setup-buildx-action from 4.1.0 to 4.2.0 ([816651f](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/816651f57b528a9384e33838227d73c5e555d363))
* **deps:** bump docker/setup-qemu-action from 3.6.0 to 4.0.0 ([6968718](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/69687189709623501c4be1422a4ce236c2d2a707))
* **deps:** bump docker/setup-qemu-action from 4.0.0 to 4.1.0 ([#69](https://github.com/Yggdrasil-Labs/valhalla-auth/issues/69)) ([31970aa](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/31970aac81a69910b48542c556d3550f3906f1ce))
* **deps:** bump docker/setup-qemu-action from 4.1.0 to 4.2.0 ([7bd3b0d](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/7bd3b0d58f876d2a6312cd92f20be1423e2e8798))
* **deps:** bump docker/setup-qemu-action from 4.1.0 to 4.2.0 ([d026cff](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/d026cff7abfda594c56ab9d0404fcfe690ec9a36))
* **deps:** bump googleapis/release-please-action from 4.4.0 to 4.4.1 ([af350d5](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/af350d52199c0bec6436b3be9fe319cc4315a0bf))
* **deps:** bump googleapis/release-please-action from 4.4.1 to 5.0.0 ([5c39f75](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/5c39f752c9760692e7bc550df2e424fa8e45026b))
* **deps:** bump softprops/action-gh-release from 2.5.0 to 3.0.0 ([49f9b31](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/49f9b3191c47a70bad8c691d6275f13734bf316d))
* **deps:** bump softprops/action-gh-release from 3.0.0 to 3.0.1 ([475ef56](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/475ef56168a830c5e29595c29183e74953dae8d9))
* **deps:** bump softprops/action-gh-release from 3.0.0 to 3.0.1 ([8aad407](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/8aad407321a7cffb7f2412a2224c620168dcfdee))
* **deps:** bump softprops/action-gh-release from 3.0.1 to 3.0.2 ([#86](https://github.com/Yggdrasil-Labs/valhalla-auth/issues/86)) ([f4d1c5c](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/f4d1c5c5a586797a5e97d45cc5af13d6fc8d5913))


### 🔧 Miscellaneous Chores

* bump version to 1.2.3-SNAPSHOT for next development cycle ([2edfcc3](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/2edfcc3085586f218dd4a726a17218a0ac5ebf1d))
* **deps-dev:** bump com.diffplug.spotless:spotless-maven-plugin ([c24f7b8](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/c24f7b89940a37e5cb19a495548ca67451edc443))
* **deps-dev:** bump com.diffplug.spotless:spotless-maven-plugin ([#73](https://github.com/Yggdrasil-Labs/valhalla-auth/issues/73)) ([23b287d](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/23b287d54143e38f51523aa27779eca7114f0f52))
* **deps-dev:** bump com.diffplug.spotless:spotless-maven-plugin ([#91](https://github.com/Yggdrasil-Labs/valhalla-auth/issues/91)) ([a01aa18](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/a01aa18b4d6fe9e5a507d047a42ba01e4b4a6d0a))
* **deps-dev:** bump org.testcontainers:junit-jupiter ([669705f](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/669705f1b2c00f8e3e426f1dacd994a5d6924906))
* **deps-dev:** bump org.testcontainers:junit-jupiter from 1.21.1 to 1.21.4 ([4fef5f0](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/4fef5f05e2d65c383496068e92441173f39a6c6f))
* **deps-dev:** bump org.testcontainers:mysql from 1.21.1 to 1.21.4 ([e44a450](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/e44a450950ca060c2e3205183eeeaa9bc30d50f8))
* **deps-dev:** bump org.testcontainers:mysql from 1.21.1 to 1.21.4 ([a03b886](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/a03b886f5591e0f08b78a1cdf748297ff8ad776e))
* **deps:** bump io.github.yggdrasil-labs:mimir-boot-bom ([#71](https://github.com/Yggdrasil-Labs/valhalla-auth/issues/71)) ([70f1389](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/70f1389ae88f6df6f2b63ace3eb582c0b0c9f967))
* **deps:** bump io.github.yggdrasil-labs:mimir-boot-parent ([#72](https://github.com/Yggdrasil-Labs/valhalla-auth/issues/72)) ([11c2025](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/11c202556930fa03b99964b6431d9e4c628a7a68))
* **deps:** bump io.jsonwebtoken:jjwt-api from 0.12.6 to 0.13.0 ([4049065](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/4049065b73f0d6a09120337c0b60d25b9b53f692))
* **deps:** bump io.jsonwebtoken:jjwt-api from 0.12.6 to 0.13.0 ([c1db2b5](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/c1db2b5d2eacd4f4c983271814ed04e5d57db235))
* **deps:** bump io.jsonwebtoken:jjwt-impl from 0.12.6 to 0.13.0 ([0c03c56](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/0c03c56de74a526f441621496e3423ea9a0f0b23))
* **deps:** bump io.jsonwebtoken:jjwt-impl from 0.12.6 to 0.13.0 ([f3dc548](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/f3dc5481daa09b742a5365a2e24176f42b850c2c))
* **deps:** bump io.jsonwebtoken:jjwt-jackson from 0.12.6 to 0.13.0 ([56526f1](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/56526f16620e85a25c744af7b09d007134d74234))
* **deps:** bump io.jsonwebtoken:jjwt-jackson from 0.12.6 to 0.13.0 ([ebf5dda](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/ebf5dda74b5fb686df8547f117e0d72ec94e31b8))
* **deps:** bump the mimir-boot group with 2 updates ([0ff0b6e](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/0ff0b6e75ae78f60f0954c7205437956824d19f9))

## [1.2.2](https://github.com/Yggdrasil-Labs/valhalla-auth/compare/v1.2.1...v1.2.2) (2026-01-31)


### 🐛 Bug Fixes

* **pom:** update mainClass path in pom.xml to reflect new package structure ([5ef0784](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/5ef07841c1d60e8b7992dddae0ab47918cc31aa1))


### 👷 Continuous Integration

* **release:** 增强 GitHub Actions 工作流程以更新 pom.xml 版本并为 GitHub Packages 部署配置 Maven ([ec6f982](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/ec6f9829d067b54e79f050ea606e2d7bd9b47849))
* **release:** 更新 pom.xml 和 GitHub Actions 以禁用 Maven Central 发布并确保仅部署到 GitHub Packages ([b262ef3](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/b262ef32fa5c212ab153de64901ac598e508b5a8))
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
