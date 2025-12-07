package com.yggdrasil.labs.app.customer.executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.cola.dto.Response;
import com.yggdrasil.labs.app.customer.convert.CustomerConverter;
import com.yggdrasil.labs.client.dto.cmd.CreateCustomerCmd;
import com.yggdrasil.labs.domain.customer.model.Customer;
import com.yggdrasil.labs.domain.customer.repository.CustomerRepository;

/**
 * CustomerCreateCmdExe：创建客户命令执行器
 *
 * <p>负责编排创建客户的流程：转换 → 校验 → 持久化
 */
@Component
public class CustomerCreateCmdExe {

    @Autowired private CustomerRepository customerRepository;

    @Autowired private CustomerConverter customerConverter;

    /**
     * 执行创建客户命令
     *
     * <p>流程：
     *
     * <ol>
     *   <li>将 Cmd 转换为 Entity
     *   <li>调用 Domain 层的业务规则校验
     *   <li>持久化到数据库
     * </ol>
     *
     * @param cmd 创建客户命令
     * @return 执行结果
     */
    @Transactional(rollbackFor = Exception.class)
    public Response execute(CreateCustomerCmd cmd) {
        // 1. Cmd → Entity
        Customer customer = customerConverter.toEntity(cmd);

        // 2. 调用 Domain 层的业务规则
        customer.validate();
        customer.checkConflict();

        // 3. 持久化
        customerRepository.save(customer);

        return Response.buildSuccess();
    }
}
