package com.yggdrasil.labs.client.dto.query;

import jakarta.validation.constraints.Size;

import com.alibaba.cola.dto.Query;

import lombok.Data;
import lombok.EqualsAndHashCode;

/** ListCustomerQuery：按名称列表查询客户 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ListCustomerQuery extends Query {

    /** 客户名称（支持模糊查询） */
    @Size(max = 100, message = "客户名称长度不能超过100个字符")
    private String name;
}
