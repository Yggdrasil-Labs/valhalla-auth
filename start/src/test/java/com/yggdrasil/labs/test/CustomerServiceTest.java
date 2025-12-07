package com.yggdrasil.labs.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.cola.dto.Response;
import com.yggdrasil.labs.client.api.CustomerClient;
import com.yggdrasil.labs.client.dto.cmd.CreateCustomerCmd;
import com.yggdrasil.labs.client.dto.enums.ErrorCode;

/**
 * This is for integration test.
 *
 * <p>Created by fulan.zjf on 2017/11/29.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerServiceTest {

    @Autowired private CustomerClient customerClient;

    @Before
    public void setUp() {}

    @Test
    public void testCustomerAddSuccess() {
        // 1.prepare
        CreateCustomerCmd cmd = new CreateCustomerCmd();
        cmd.setCustomerId("TEST001");
        cmd.setCustomerName("测试客户");
        cmd.setCustomerType("ENTERPRISE");
        cmd.setCompanyName("NormalName");

        // 2.execute
        Response response = customerClient.createCustomer(cmd);

        // 3.assert
        Assert.assertTrue(response.isSuccess());
    }

    @Test
    public void testCustomerAddCompanyNameConflict() {
        // 1.prepare
        CreateCustomerCmd cmd = new CreateCustomerCmd();
        cmd.setCustomerId("TEST002");
        cmd.setCustomerName("测试客户2");
        cmd.setCustomerType("ENTERPRISE");
        cmd.setCompanyName("ConflictCompanyName");

        // 2.execute
        Response response = customerClient.createCustomer(cmd);

        // 3.assert error
        Assert.assertEquals(
                ErrorCode.B_CUSTOMER_companyNameConflict.getErrCode(), response.getErrCode());
    }
}
