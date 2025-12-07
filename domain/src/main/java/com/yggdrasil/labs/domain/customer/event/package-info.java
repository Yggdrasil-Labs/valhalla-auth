/**
 * 领域事件（Domain Event）
 *
 * <p>本包定义 Customer 聚合的领域事件。
 *
 * <p><b>核心职责：</b>
 *
 * <ul>
 *   <li>定义领域事件类，记录领域内发生的重要业务变化
 *   <li>支持事件驱动架构（EDA）
 *   <li>实现聚合之间的解耦通信
 * </ul>
 *
 * <p><b>设计原则：</b>
 *
 * <ul>
 *   <li><b>过去时命名</b>：事件名称使用过去时，表示已发生的事实（如 CustomerCreatedEvent）
 *   <li><b>不可变对象</b>：事件对象应该是不可变的，使用 final 字段
 *   <li><b>包含上下文</b>：事件应包含完整的业务上下文信息
 *   <li><b>事件溯源</b>：事件可用于事件溯源（Event Sourcing）
 * </ul>
 *
 * <p><b>事件定义示例：</b>
 *
 * <pre>{@code
 * @Data
 * @EqualsAndHashCode(callSuper = false)
 * public class CustomerCreatedEvent extends DomainEvent {
 *
 *     // 客户ID
 *     private final String customerId;
 *
 *     // 客户名称
 *     private final String customerName;
 *
 *     // 客户类型
 *     private final CustomerType customerType;
 *
 *     // 公司名称
 *     private final String companyName;
 *
 *     // 事件时间
 *     private final LocalDateTime occurredOn;
 *
 *     public CustomerCreatedEvent(String customerId, String customerName,
 *                                 CustomerType customerType, String companyName) {
 *         this.customerId = customerId;
 *         this.customerName = customerName;
 *         this.customerType = customerType;
 *         this.companyName = companyName;
 *         this.occurredOn = LocalDateTime.now();
 *     }
 * }
 * }</pre>
 *
 * <p><b>事件发布示例：</b>
 *
 * <pre>{@code
 * // 在 Domain Entity 中发布事件
 * public class Customer extends AggregateRoot<String> {
 *
 *     public void create() {
 *         // 执行业务逻辑
 *         this.validate();
 *
 *         // 发布领域事件
 *         this.registerEvent(new CustomerCreatedEvent(
 *             this.customerId,
 *             this.customerName,
 *             this.customerType,
 *             this.companyName
 *         ));
 *     }
 * }
 * }</pre>
 *
 * <p><b>事件处理示例：</b>
 *
 * <pre>{@code
 * // 在 App 层或 Infrastructure 层监听事件
 * @Component
 * public class CustomerEventListener {
 *
 *     @EventListener
 *     public void handleCustomerCreated(CustomerCreatedEvent event) {
 *         // 发送欢迎邮件
 *         sendWelcomeEmail(event.getCustomerId());
 *
 *         // 发送 MQ 消息
 *         sendMQMessage(event);
 *
 *         // 更新缓存
 *         updateCache(event.getCustomerId());
 *     }
 * }
 * }</pre>
 *
 * <p><b>常见领域事件：</b>
 *
 * <ul>
 *   <li><b>CustomerCreatedEvent</b>：客户创建事件
 *   <li><b>CustomerUpdatedEvent</b>：客户信息更新事件
 *   <li><b>CustomerDeletedEvent</b>：客户删除事件
 *   <li><b>CustomerStatusChangedEvent</b>：客户状态变更事件
 * </ul>
 *
 * <p><b>命名规范：</b>
 *
 * <ul>
 *   <li>事件类命名：{Domain}{Action}Event（如 CustomerCreatedEvent）
 *   <li>使用过去时动词：Created、Updated、Deleted、Changed
 *   <li>包名：domain.{aggregate}.event
 * </ul>
 *
 * <p><b>注意事项：</b>
 *
 * <ul>
 *   <li>领域事件应在 Domain 层定义，在 App 或 Infrastructure 层处理
 *   <li>事件对象应该是不可变的，所有字段使用 final 修饰
 *   <li>事件应包含足够的上下文信息，避免事件处理器需要回查数据库
 *   <li>事件处理应该是异步的，避免影响主流程性能
 *   <li>事件处理失败应该有重试机制和补偿机制
 *   <li>避免在事件处理中包含复杂的业务逻辑
 * </ul>
 *
 * @author Midgard Team
 */
package com.yggdrasil.labs.domain.customer.event;
