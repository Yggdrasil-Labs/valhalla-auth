/**
 * Customer 领域仓储接口包
 *
 * <p>本包包含 Customer 领域的仓储接口定义，用于定义领域对象与持久化层的交互契约。
 *
 * <p><b>职责：</b>
 *
 * <ul>
 *   <li>定义 Customer 领域对象的仓储接口（Repository Interface）
 *   <li>定义仓储接口的查询方法
 *   <li>遵循领域层仓储命名规范
 * </ul>
 *
 * <p><b>注意事项：</b>
 *
 * <ul>
 *   <li>本包只包含接口定义，不包含实现
 *   <li>实现应在基础设施层（Infrastructure Layer）完成
 *   <li>仓储接口应面向领域模型，而非数据库表结构
 * </ul>
 */
package com.yggdrasil.labs.domain.customer.repository;
