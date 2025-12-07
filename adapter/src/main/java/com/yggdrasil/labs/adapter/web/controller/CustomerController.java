package com.yggdrasil.labs.adapter.web.controller;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.Response;
import com.yggdrasil.labs.adapter.web.convert.CustomerWebConverter;
import com.yggdrasil.labs.adapter.web.request.CreateCustomerRequest;
import com.yggdrasil.labs.client.api.CustomerClient;
import com.yggdrasil.labs.client.dto.cmd.CreateCustomerCmd;
import com.yggdrasil.labs.client.dto.co.CustomerCO;
import com.yggdrasil.labs.client.dto.query.ListCustomerQuery;

/**
 * 客户管理 Controller
 *
 * <p>提供客户相关的 REST API
 */
@Validated
@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Resource private CustomerClient customerClient;
    @Resource private CustomerWebConverter customerWebConverter;

    /**
     * 按名称查询客户列表
     *
     * @param name 客户名称（支持模糊查询）
     * @return 客户列表
     */
    @GetMapping(value = "/list")
    public MultiResponse<CustomerCO> listCustomerByName(
            @RequestParam(required = false) @Size(max = 100, message = "客户名称长度不能超过100个字符")
                    String name) {
        ListCustomerQuery query = new ListCustomerQuery();
        query.setName(name);
        return customerClient.listCustomerByName(query);
    }

    /**
     * 创建客户
     *
     * @param body 创建客户请求
     * @return 创建结果
     */
    @PostMapping(value = "/add")
    public Response createCustomer(@Valid @RequestBody CreateCustomerRequest body) {
        CreateCustomerCmd cmd = customerWebConverter.toCreateCustomerCmd(body);
        return customerClient.createCustomer(cmd);
    }
}
