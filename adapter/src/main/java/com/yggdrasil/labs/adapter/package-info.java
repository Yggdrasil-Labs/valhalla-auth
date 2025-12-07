/**
 * 适配层（Adapter Layer）
 *
 * <p>本包是 COLA 5.0 架构的 Adapter 层，负责所有外部接口的适配和转换。
 *
 * <p><b>核心职责：</b>
 *
 * <ul>
 *   <li>接收外部请求并适配到内部接口（入站适配器）
 *   <li>处理各种协议的输入输出（HTTP、RPC、MQ、定时任务等）
 *   <li>将外部请求转换为 Command 或 Query
 *   <li>调用 Client 层接口并返回结果
 * </ul>
 *
 * <p><b>包结构：</b>
 *
 * <ul>
 *   <li><b>web</b>：HTTP 接口适配
 *       <ul>
 *         <li>controller：REST 控制器
 *         <li>request：Web 请求对象
 *         <li>convert：Request ↔ Command/Query 转换
 *         <li>filter：Servlet Filter
 *         <li>interceptor：HandlerInterceptor
 *         <li>handler：异常和返回值处理器
 *       </ul>
 *   <li><b>rpc</b>：RPC 接口适配（Dubbo、GRPC）
 *       <ul>
 *         <li>provider：RPC 服务提供者
 *         <li>consumer：RPC 服务消费者
 *       </ul>
 *   <li><b>feign</b>：Feign 客户端适配
 *       <ul>
 *         <li>client：Feign 接口定义
 *         <li>fallback：降级处理
 *       </ul>
 *   <li><b>mq</b>：消息队列适配
 *       <ul>
 *         <li>producer：消息生产者
 *         <li>consumer：消息消费者
 *       </ul>
 *   <li><b>mobile</b>：移动端适配器
 *   <li><b>wap</b>：WAP 端适配器
 *   <li><b>schedule</b>：定时任务适配
 *   <li><b>oss</b>：对象存储适配
 * </ul>
 *
 * <p><b>设计原则：</b>
 *
 * <ul>
 *   <li><b>协议转换</b>：将外部协议（HTTP、RPC、MQ）转换为内部统一接口调用
 *   <li><b>薄适配层</b>：只做协议转换和数据映射，不包含业务逻辑
 *   <li><b>参数校验</b>：在 Request 对象上使用 JSR 303 注解进行参数校验
 *   <li><b>异常处理</b>：统一处理异常并转换为合适的响应格式
 *   <li><b>日志记录</b>：记录请求和响应日志，方便问题排查
 * </ul>
 *
 * <p><b>转换链路：</b>
 *
 * <pre>
 * HTTP Request (JSON)
 *   ↓ [Jackson 反序列化]
 * Request DTO (Adapter)
 *   ↓ [Converter - MapStruct]
 * Command/Query (Client)
 *   ↓ [调用 Client API]
 * Response/CO (Client)
 *   ↓ [Jackson 序列化]
 * HTTP Response (JSON)
 * </pre>
 *
 * <p><b>依赖关系：</b>
 *
 * <ul>
 *   <li><b>依赖</b>：依赖 Client 层接口和 DTO
 *   <li><b>不依赖</b>：不直接依赖 App、Domain、Infrastructure 层
 *   <li><b>被依赖</b>：Start 层依赖 Adapter 层（扫描 Controller 等）
 * </ul>
 *
 * <p><b>注意事项：</b>
 *
 * <ul>
 *   <li>Adapter 层只做适配转换，不包含业务逻辑
 *   <li>Controller 方法应简洁，只做参数转换和接口调用
 *   <li>使用 @Validated 和 @Valid 启用参数校验
 *   <li>统一异常处理应在 web.handler 包中实现
 *   <li>不同的外部协议（Web、RPC、MQ）应分别适配
 * </ul>
 *
 * @author YoungerYang-Y
 */
package com.yggdrasil.labs.adapter;
