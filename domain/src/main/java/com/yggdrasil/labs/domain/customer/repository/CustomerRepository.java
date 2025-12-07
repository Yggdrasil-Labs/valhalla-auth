package com.yggdrasil.labs.domain.customer.repository;

import java.util.List;

import com.yggdrasil.labs.domain.customer.model.Customer;

/**
 * Customer 领域仓储接口
 *
 * <p>负责 Customer 领域对象的持久化操作
 *
 * <p><b>职责：</b>
 *
 * <ul>
 *   <li>提供 Customer 实体的持久化能力
 *   <li>封装数据访问细节，对 Domain 层提供领域友好的接口
 *   <li>由 Infrastructure 层实现具体的数据访问逻辑
 * </ul>
 */
public interface CustomerRepository {

    /**
     * 根据客户ID查询客户
     *
     * @param customerId 客户ID
     * @return 客户实体，不存在返回 null
     */
    Customer findById(String customerId);

    /**
     * 根据客户名称模糊查询
     *
     * @param name 客户名称
     * @return 客户列表
     */
    List<Customer> findByNameLike(String name);

    /**
     * 保存客户（新增或更新）
     *
     * @param customer 客户实体
     */
    void save(Customer customer);

    /**
     * 更新客户信息
     *
     * @param customer 客户实体
     */
    void update(Customer customer);

    /**
     * 删除客户
     *
     * @param customerId 客户ID
     */
    void delete(String customerId);

    /**
     * 检查公司名称是否已存在
     *
     * @param companyName 公司名称
     * @return 存在返回 true，否则返回 false
     */
    boolean existsByCompanyName(String companyName);
}
