package com.yggdrasil.labs.adapter.web.request;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

/**
 * Token 刷新请求
 *
 * @author YoungerYang-Y
 */
@Data
public class RefreshTokenRequest {

    /** 刷新令牌 */
    @NotBlank(message = "刷新令牌不能为空")
    private String refreshToken;

    /** 设备ID（可选） */
    private String deviceId;

    /** IP地址（可选） */
    private String ipAddress;

    /** 用户代理（可选） */
    private String userAgent;
}
