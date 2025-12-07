/**
 * 客户领域模型（Domain 层）
 *
 * <p>本包属于 DDD 架构中的 Domain 层（领域层），包含客户相关的领域模型。
 *
 * <p><b>职责：</b>
 *
 * <ul>
 *   <li>定义客户领域实体（Entity）
 *   <li>实现客户相关的业务逻辑
 *   <li>定义客户领域值对象（Value Object）
 *   <li>定义客户领域枚举和常量
 * </ul>
 *
 * <p><b>使用场景：</b>
 *
 * <ul>
 *   <li>实现客户相关的核心业务逻辑
 *   <li>定义客户领域模型和业务规则
 * </ul>
 *
 * <p><b>注意事项：</b>
 *
 * <ul>
 *   <li>本层不依赖任何外部框架，应为纯 Java 对象
 *   <li>业务逻辑应在领域实体或领域服务中实现
 *   <li>领域模型应保持高内聚、低耦合
 *   <li>避免贫血模型，领域对象应包含行为
 * </ul>
 *
 * <p>这是 Midgard 模板项目的一部分，用于快速开始其他具体的业务项目。
 *
 * @author Midgard Team
 */
package com.yggdrasil.labs.domain.customer;
