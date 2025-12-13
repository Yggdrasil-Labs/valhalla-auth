/**
 * Client API 实现包
 *
 * <p>本包负责实现 Client 层定义的接口（如 AuthClient）
 *
 * <p><b>职责：</b>
 *
 * <ul>
 *   <li>实现 Client 层定义的接口
 *   <li>编排业务用例，调用 Executor 和 Query 服务
 *   <li>作为 App 层对外提供业务能力的入口
 * </ul>
 *
 * <p><b>命名规范：</b>
 *
 * <ul>
 *   <li>实现类命名：{Domain}ClientImpl
 *   <li>示例：AuthClientImpl、CustomerClientImpl
 * </ul>
 *
 * <p><b>与 Client 层的对应关系：</b>
 *
 * <ul>
 *   <li>Client 层：client/api/{Domain}Client.java（接口定义）
 *   <li>App 层：app/{domain}/api/impl/{Domain}ClientImpl.java（接口实现）
 * </ul>
 *
 * @author YoungerYang-Y
 */
package com.yggdrasil.labs.app.auth.api.impl;
