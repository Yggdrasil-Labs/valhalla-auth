package com.yggdrasil.labs.app.customer.query;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.cola.dto.MultiResponse;
import com.yggdrasil.labs.app.customer.assembler.CustomerAssembler;
import com.yggdrasil.labs.client.dto.co.CustomerCO;
import com.yggdrasil.labs.client.dto.query.ListCustomerQuery;
import com.yggdrasil.labs.domain.customer.model.Customer;
import com.yggdrasil.labs.domain.customer.repository.CustomerRepository;

/**
 * CustomerListQryExe：查询客户列表执行器
 *
 * <p>负责编排查询客户的流程：查询 → 组装
 */
@Component
public class CustomerListQryExe {

    @Autowired private CustomerRepository customerRepository;

    @Autowired private CustomerAssembler customerAssembler;

    /**
     * 执行查询客户列表
     *
     * <p>流程：
     *
     * <ol>
     *   <li>调用 Repository 查询
     *   <li>使用 Assembler 转换为 CO
     * </ol>
     *
     * @param query 查询条件
     * @return 客户列表
     */
    public MultiResponse<CustomerCO> execute(ListCustomerQuery query) {
        // 1. 查询 Domain Entity
        List<Customer> customerList = customerRepository.findByNameLike(query.getName());

        // 2. Entity → CO
        List<CustomerCO> customerCOList = customerAssembler.toCustomerCOList(customerList);

        return MultiResponse.of(customerCOList);
    }
}
