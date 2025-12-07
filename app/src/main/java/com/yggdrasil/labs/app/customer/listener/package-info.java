/**
 * 事件监听器（Event Listener）
 *
 * <p>本包负责监听和处理 Customer 聚合相关的领域事件。
 *
 * <p><b>核心职责：</b>
 *
 * <ul>
 *   <li>监听 Domain 层发布的领域事件
 *   <li>执行事件处理逻辑（如发送通知、更新缓存、发送 MQ 消息）
 *   <li>协调跨聚合的业务流程
 *   <li>处理异步任务和补偿逻辑
 * </ul>
 *
 * <p><b>设计原则：</b>
 *
 * <ul>
 *   <li><b>异步处理</b>：事件处理应该是异步的，避免阻塞主流程
 *   <li><b>幂等性</b>：事件处理应该是幂等的，支持重复消费
 *   <li><b>事务边界</b>：事件处理应在独立的事务中执行
 *   <li><b>失败重试</b>：事件处理失败应有重试机制
 *   <li><b>补偿机制</b>：关键业务失败应有补偿机制
 * </ul>
 *
 * <p><b>事件监听器示例：</b>
 *
 * <pre>{@code
 * @Component
 * public class CustomerEventListener {
 *
 *     @Resource
 *     private EmailService emailService;
 *
 *     @Resource
 *     private CacheService cacheService;
 *
 *     @Resource
 *     private MQProducer mqProducer;
 *
 *     // 监听客户创建事件
 *     @EventListener
 *     @Async
 *     public void handleCustomerCreated(CustomerCreatedEvent event) {
 *         log.info("处理客户创建事件: customerId={}", event.getCustomerId());
 *
 *         // 发送欢迎邮件
 *         sendWelcomeEmail(event);
 *
 *         // 发送 MQ 消息到其他系统
 *         publishToMQ(event);
 *
 *         // 更新缓存
 *         updateCache(event);
 *     }
 *
 *     // 监听客户更新事件
 *     @EventListener
 *     @Async
 *     public void handleCustomerUpdated(CustomerUpdatedEvent event) {
 *         log.info("处理客户更新事件: customerId={}", event.getCustomerId());
 *
 *         // 清除缓存
 *         cacheService.evict("customer:" + event.getCustomerId());
 *
 *         // 发送 MQ 消息
 *         mqProducer.send("customer.updated", event);
 *     }
 *
 *     private void sendWelcomeEmail(CustomerCreatedEvent event) {
 *         try {
 *             emailService.sendWelcomeEmail(event.getCustomerId());
 *         } catch (Exception e) {
 *             log.error("发送欢迎邮件失败: customerId={}", event.getCustomerId(), e);
 *             // 可以记录失败日志，后续补偿
 *         }
 *     }
 * }
 * }</pre>
 *
 * <p><b>使用 Spring Event 示例：</b>
 *
 * <pre>{@code
 * // 在 Executor 中发布事件
 * @Service
 * public class CustomerCreateCmdExe {
 *
 *     @Resource
 *     private ApplicationEventPublisher eventPublisher;
 *
 *     @Transactional
 *     public Response execute(CreateCustomerCmd cmd) {
 *         // 执行业务逻辑
 *         Customer customer = createCustomer(cmd);
 *
 *         // 发布领域事件
 *         CustomerCreatedEvent event = new CustomerCreatedEvent(
 *             customer.getCustomerId(),
 *             customer.getCustomerName(),
 *             customer.getCustomerType(),
 *             customer.getCompanyName()
 *         );
 *         eventPublisher.publishEvent(event);
 *
 *         return Response.buildSuccess();
 *     }
 * }
 * }</pre>
 *
 * <p><b>监听器配置：</b>
 *
 * <pre>{@code
 * // 在 Start 层配置异步事件处理
 * @Configuration
 * @EnableAsync
 * public class AsyncEventConfig {
 *
 *     @Bean(name = "eventExecutor")
 *     public Executor eventExecutor() {
 *         ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
 *         executor.setCorePoolSize(5);
 *         executor.setMaxPoolSize(10);
 *         executor.setQueueCapacity(100);
 *         executor.setThreadNamePrefix("event-");
 *         executor.initialize();
 *         return executor;
 *     }
 * }
 * }</pre>
 *
 * <p><b>命名规范：</b>
 *
 * <ul>
 *   <li>监听器类命名：{Domain}EventListener（如 CustomerEventListener）
 *   <li>监听方法命名：handle{EventName}（如 handleCustomerCreated）
 *   <li>包名：app.{aggregate}.listener
 * </ul>
 *
 * <p><b>注意事项：</b>
 *
 * <ul>
 *   <li>事件监听器应在 App 层或 Infrastructure 层，不在 Domain 层
 *   <li>监听器方法应使用 @Async 注解，避免阻塞主流程
 *   <li>监听器处理失败不应影响主流程（事务已提交）
 *   <li>关键业务失败应记录日志，并提供补偿机制
 *   <li>避免在监听器中执行长时间的同步操作
 *   <li>监听器之间应避免循环依赖和事件风暴
 *   <li>使用 @Order 注解控制多个监听器的执行顺序
 * </ul>
 *
 * @author YoungerYang-Y
 */
package com.yggdrasil.labs.app.customer.listener;
