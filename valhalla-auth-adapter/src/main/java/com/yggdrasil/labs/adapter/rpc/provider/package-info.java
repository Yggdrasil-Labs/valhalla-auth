/**
 * RPC 服务提供者
 *
 * <p>本包属于 Adapter 层的 RPC 适配模块，负责提供 Dubbo RPC 服务。
 *
 * <p><b>职责：</b>
 *
 * <ul>
 *   <li>定义 RPC 服务接口
 *   <li>实现 RPC 服务，调用 Client 层接口
 *   <li>将 RPC 请求转换为内部 Command/Query 对象
 * </ul>
 *
 * <p><b>设计原则：</b>
 *
 * <ul>
 *   <li>RPC 服务接口应复用 Client 层接口，避免重复定义
 *   <li>RPC 服务实现类应调用 Client 层接口，不包含业务逻辑
 *   <li>使用 Dubbo 注解（@DubboService）标记服务提供者
 * </ul>
 *
 * @author YoungerYang-Y
 */
package com.yggdrasil.labs.adapter.rpc.provider;
