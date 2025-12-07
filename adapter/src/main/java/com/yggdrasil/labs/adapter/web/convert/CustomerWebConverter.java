package com.yggdrasil.labs.adapter.web.convert;

import org.mapstruct.Mapper;

import com.yggdrasil.labs.adapter.web.request.CreateCustomerRequest;
import com.yggdrasil.labs.client.dto.cmd.CreateCustomerCmd;

/** CustomerWebConverter：适配 Web Request 到 Client Cmd 的对象转换 */
@Mapper(componentModel = "spring")
public interface CustomerWebConverter {

    /** CustomerRequest -> CreateCustomerCmd */
    CreateCustomerCmd toCreateCustomerCmd(CreateCustomerRequest request);
}
