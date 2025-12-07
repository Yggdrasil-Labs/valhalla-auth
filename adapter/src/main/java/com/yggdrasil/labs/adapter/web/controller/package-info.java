/**
 * HTTP 接口适配 - REST 控制器
 *
 * <p>本包属于 Adapter 层，负责处理 Web 端的 HTTP 请求。
 *
 * <p><b>职责：</b>
 *
 * <ul>
 *   <li>接收和处理 HTTP 请求
 *   <li>参数校验和格式转换
 *   <li>调用应用层服务
 *   <li>返回统一格式的响应
 * </ul>
 *
 * <p><b>使用场景：</b>
 *
 * <ul>
 *   <li>为 Web 前端提供 RESTful API 接口
 *   <li>处理 Web 端的业务请求
 * </ul>
 *
 * <p><b>注意事项：</b>
 *
 * <ul>
 *   <li>本层为薄层，不包含业务逻辑，仅负责请求转发
 *   <li>所有业务逻辑应在 APP 层或 Domain 层实现
 *   <li>参数校验可使用 JSR 303 注解或手动校验
 * </ul>
 *
 * @author Midgard Team
 */
package com.yggdrasil.labs.adapter.web.controller;
