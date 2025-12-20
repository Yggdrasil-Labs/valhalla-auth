# Changelog

## 1.0.0 (2025-12-20)


### ✨ Features

* client层实现对外契约 ([65d02fe](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/65d02fe46d9ba37876986ac1d57771762a16cead))
* infra实现仓储接口与转换器 ([4d1b594](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/4d1b594b1fb7c59315d68c13fb281e04ce88122f))
* 完成认证服务整体 ([75db7f3](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/75db7f3c284929c3bd69fd9e53dcd27f3f66704b))
* 支持容器化构建 ([9f83261](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/9f8326198408212158f32d9c111612f69e45f474))
* 新增DO ([3d1097e](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/3d1097e4ab6f14a983586f32b91b82e63eff6e19))
* 新增数据库设计 ([cb2c2bf](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/cb2c2bf337551f4c33b81ef4c37ec0e29fb2e1a9))
* 新增领域实体与仓储接口 ([5398734](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/5398734ec328cbddb3362473a245f038c7ee05a6))


### 🐛 Bug Fixes

* 使用mimir-boot发布版 ([a7c2e54](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/a7c2e5473a8ae40b8001ab2084c25f9f66331e06))
* 禁用dubbo的配置中心、元数据中心（启动时控制台会有warn、error，但是不影响） ([486265e](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/486265e1d4654b0dbc9fa0d524318ace6d1ca8b9))
* 统一镜像的组织 ([59682cb](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/59682cbb3998175c3dd5d9e91e1772d271b2b62c))


### ♻️ Code Refactoring

* 清理无关代码 ([d80829d](https://github.com/Yggdrasil-Labs/valhalla-auth/commit/d80829db3a82b2e3ff9e16e71860e1462858c4ca))


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
