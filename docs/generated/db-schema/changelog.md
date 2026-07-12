# db-schema 变更日志

<!-- ⚠️ 本文件由工具自动生成，请勿手动编辑 -->

## 2026-07-12 — 初始生成

来源迁移脚本：`V1.0.0__create_auth_tables.sql`

新增表：

- **auth_password** — 用户密码表（密码哈希、算法、状态、过期策略）
- **auth_credential** — 登录凭证表（多凭证类型：用户名/手机/邮箱/OAuth）
- **auth_mfa_factor** — 多因子认证表（TOTP/SMS/EMAIL/U2F 配置）
