package com.yggdrasil.labs.adapter.web.request;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

/**
 * Token 验证请求
 *
 * @author YoungerYang-Y
 */
@Data
public class VerifyTokenRequest {

    /** Token值 */
    @NotBlank(message = "Token不能为空")
    private String token;
}
