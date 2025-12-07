package com.yggdrasil.labs.app.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.cola.catchlog.CatchAndLog;
import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.Response;
import com.yggdrasil.labs.app.customer.executor.CustomerCreateCmdExe;
import com.yggdrasil.labs.app.customer.query.CustomerListQryExe;
import com.yggdrasil.labs.client.api.CustomerClient;
import com.yggdrasil.labs.client.dto.cmd.CreateCustomerCmd;
import com.yggdrasil.labs.client.dto.co.CustomerCO;
import com.yggdrasil.labs.client.dto.query.ListCustomerQuery;

@Service
@CatchAndLog
public class CustomerClientImpl implements CustomerClient {

    @Autowired private CustomerCreateCmdExe customerCreateCmdExe;

    @Autowired private CustomerListQryExe customerListQryExe;

    @Override
    public Response createCustomer(CreateCustomerCmd cmd) {
        return customerCreateCmdExe.execute(cmd);
    }

    @Override
    public MultiResponse<CustomerCO> listCustomerByName(ListCustomerQuery query) {
        return customerListQryExe.execute(query);
    }
}
