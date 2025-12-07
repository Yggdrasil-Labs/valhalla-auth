package com.yggdrasil.labs.adapter.web.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.Data;

/** CreateCustomerRequest：Web 层创建客户请求 */
@Data
public class CreateCustomerRequest {

    /** 客户ID */
    @NotBlank(message = "客户ID不能为空")
    @Size(max = 50, message = "客户ID长度不能超过50个字符")
    private String customerId;

    /** 会员ID */
    @Size(max = 50, message = "会员ID长度不能超过50个字符")
    private String memberId;

    /** 客户名称 */
    @NotBlank(message = "客户名称不能为空")
    @Size(max = 100, message = "客户名称长度不能超过100个字符")
    private String customerName;

    /** 客户类型 */
    @NotBlank(message = "客户类型不能为空")
    @Pattern(regexp = "^(ENTERPRISE|PERSONAL)$", message = "客户类型必须是 ENTERPRISE 或 PERSONAL")
    private String customerType;

    /** 公司名称 */
    @NotBlank(message = "公司名称不能为空")
    @Size(max = 200, message = "公司名称长度不能超过200个字符")
    private String companyName;

    /** 来源 */
    @Pattern(regexp = "^(WEB|APP|API|WECHAT)$", message = "来源必须是 WEB、APP、API 或 WECHAT")
    private String source;
}
