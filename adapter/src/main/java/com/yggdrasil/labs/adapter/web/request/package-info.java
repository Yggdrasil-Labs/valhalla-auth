/**
 * Web 端请求对象
 *
 * <p>本包属于 Adapter 层的 Web 适配模块，定义 Web 端 HTTP 请求的数据结构。
 *
 * <p><b>职责：</b>
 *
 * <ul>
 *   <li>定义 Web 端 HTTP 请求的参数结构
 *   <li>接收前端传递的请求数据
 *   <li>进行基本的参数校验（使用 JSR 303 注解）
 * </ul>
 *
 * <p><b>类命名规则：</b>
 *
 * <ul>
 *   <li>命名格式：{Verb}{Domain}Request
 *   <li>示例：CreateCustomerRequest、UpdateOrderRequest、ListProductRequest
 *   <li>动词遵循固定映射表：Create/Update/Modify/Add/Delete/Remove/Get/Query/List/Page/Check
 * </ul>
 *
 * <p><b>设计原则：</b>
 *
 * <ul>
 *   <li>使用 Lombok 的 @Data 注解简化代码
 *   <li>使用 JSR 303 注解进行参数校验（@NotNull、@NotEmpty、@Valid 等）
 *   <li>字段命名应与前端保持一致，便于对接
 *   <li>只包含 Web 端需要的字段，不包含业务层的复杂对象
 * </ul>
 *
 * <p><b>使用场景：</b>
 *
 * <ul>
 *   <li>作为 Controller 方法的 @RequestBody 或 @RequestParam 参数
 *   <li>接收前端表单提交、JSON 请求等数据
 * </ul>
 *
 * <p><b>注意事项：</b>
 *
 * <ul>
 *   <li>Request 对象只用于接收数据，不包含业务逻辑
 *   <li>参数校验应使用 JSR 303 注解，复杂校验在 App 层进行
 *   <li>Request 对象会被 Convertor 转换为 Client 层的 Cmd/Query 对象
 *   <li>不同端的 Request 对象可以有不同的字段和结构
 * </ul>
 *
 * @author Midgard Team
 */
package com.yggdrasil.labs.adapter.web.request;
