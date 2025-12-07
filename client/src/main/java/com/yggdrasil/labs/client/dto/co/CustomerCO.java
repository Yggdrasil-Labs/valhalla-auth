package com.yggdrasil.labs.client.dto.co;

import com.alibaba.cola.dto.DTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

/** CustomerCO：客户对外展示数据 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CustomerCO extends DTO {
    private String customerId;
    private String memberId;
    private String customerName;
    private String customerType;
    private String companyName;
    private String source;
}
