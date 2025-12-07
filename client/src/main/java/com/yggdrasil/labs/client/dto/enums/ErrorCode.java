package com.yggdrasil.labs.client.dto.enums;

import lombok.Getter;

@Getter
public enum ErrorCode {
    B_CUSTOMER_companyNameConflict("B_CUSTOMER_companyNameConflict", "客户公司名冲突");

    private final String errCode;
    private final String errDesc;

    ErrorCode(String errCode, String errDesc) {
        this.errCode = errCode;
        this.errDesc = errDesc;
    }
}
