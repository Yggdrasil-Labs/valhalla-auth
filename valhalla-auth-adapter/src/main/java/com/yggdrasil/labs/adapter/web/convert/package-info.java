/**
 * Web 端对象转换器
 *
 * <p>本包属于 Adapter 层的 Web 适配模块，负责将 Web 端的 Request 对象转换为 Client 层的 Cmd/Query 对象。
 *
 * <p><b>职责：</b>
 *
 * <ul>
 *   <li>将 Web Request 对象转换为 Client Command 对象
 *   <li>将 Web Request 对象转换为 Client Query 对象
 *   <li>处理字段映射和格式转换
 * </ul>
 *
 * <p><b>类命名规则：</b>
 *
 * <ul>
 *   <li>命名格式：{Domain}WebConvertor
 *   <li>示例：CustomerWebConverter、OrderWebConverter
 *   <li>使用 MapStruct 注解实现转换逻辑
 * </ul>
 *
 * <p><b>方法命名规则：</b>
 *
 * <ul>
 *   <li>Request → Cmd：to{Verb}{Domain}Cmd
 *   <li>示例：toCreateCustomerCmd(CreateCustomerRequest)
 *   <li>Request → Query：to{Verb}{Domain}Query
 *   <li>示例：toListCustomerQuery(ListCustomerRequest)
 * </ul>
 *
 * <p><b>使用场景：</b>
 *
 * <ul>
 *   <li>在 Controller 中调用，将 HTTP 请求体转换为应用层可识别的对象
 *   <li>处理不同层之间的数据结构差异
 * </ul>
 *
 * <p><b>注意事项：</b>
 *
 * <ul>
 *   <li>推荐使用 MapStruct 进行对象转换，编译时生成代码，性能优异
 *   <li>转换器应该是无状态的，只负责数据映射
 *   <li>复杂的业务转换逻辑应在 App 层的 Assembler 中处理
 *   <li>保持转换逻辑简单，避免在转换器中添加业务校验
 * </ul>
 *
 * @author YoungerYang-Y
 */
package com.yggdrasil.labs.adapter.web.convert;
