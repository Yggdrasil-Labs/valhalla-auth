/**
 * Web 适配器（Web Adapter）
 *
 * <p>本包负责 HTTP 协议的适配，将 HTTP 请求转换为 Command/Query，调用 Client 接口。
 *
 * <p><b>核心职责：</b>
 *
 * <ul>
 *   <li>接收 HTTP 请求（REST API）
 *   <li>将 Request DTO 转换为 Command/Query
 *   <li>调用 Client 接口获取结果
 *   <li>返回 Response 给前端
 * </ul>
 *
 * <p><b>包结构：</b>
 *
 * <ul>
 *   <li><b>controller</b>：REST 控制器，处理 HTTP 请求
 *   <li><b>request</b>：Web 请求对象（Request DTO）
 *   <li><b>convert</b>：Request ↔ Command/Query 转换器（MapStruct）
 *   <li><b>filter</b>：Servlet Filter（如日志、鉴权）
 *   <li><b>interceptor</b>：HandlerInterceptor（如日志、鉴权）
 *   <li><b>handler</b>：异常和返回值处理器
 * </ul>
 *
 * <p><b>设计原则：</b>
 *
 * <ul>
 *   <li><b>RESTful 风格</b>：遵循 RESTful API 设计规范
 *   <li><b>薄 Controller</b>：Controller 只做协议转换和接口调用，不包含业务逻辑
 *   <li><b>参数校验</b>：使用 @Validated 和 @Valid 启用 JSR 303 校验
 *   <li><b>统一响应</b>：使用 COLA Response 统一返回格式
 *   <li><b>统一异常</b>：使用 @ControllerAdvice 统一处理异常
 * </ul>
 *
 * <p><b>Controller 示例：</b>
 *
 * <pre>
 * {
 *     &#64;code
 *     &#64;RestController
 *     &#64;RequestMapping("/api/customers")
 *     &#64;Validated
 *     public class CustomerController {
 *
 *         &#64;Resource
 *         private CustomerClient customerClient;
 *
 *         &#64;Resource
 *         private CustomerWebConverter converter;
 *
 *         @PostMapping
 *         public Response createCustomer(@Valid @RequestBody CreateCustomerRequest request) {
 *             CreateCustomerCmd cmd = converter.toCmd(request);
 *             return customerClient.createCustomer(cmd);
 *         }
 *     }
 * }
 * </pre>
 *
 * <p><b>依赖关系：</b>
 *
 * <ul>
 *   <li><b>依赖</b>：依赖 Client 层接口和 DTO
 *   <li><b>不依赖</b>：不直接依赖 App、Domain、Infrastructure 层
 * </ul>
 *
 * <p><b>注意事项：</b>
 *
 * <ul>
 *   <li>Controller 方法应简洁，避免复杂的业务逻辑
 *   <li>Request DTO 与 Command/Query 保持一致的校验规则
 *   <li>使用 MapStruct 进行对象转换，避免手动映射
 *   <li>所有 Controller 都应添加 @Validated 注解
 *   <li>统一异常处理应在 handler 包中实现
 * </ul>
 *
 * @author Midgard Team
 */
package com.yggdrasil.labs.adapter.web;
