package com.yggdrasil.labs.adapter.web.request;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

/**
 * 登出请求
 *
 * @author YoungerYang-Y
 */
@Data
public class LogoutRequest {

    /** 用户ID */
    @NotNull(message = "用户ID不能为空")
    private Long userId;

    /** 访问令牌（可选，用于撤销特定Token） */
    private String accessToken;

    /** 设备ID（可选，用于撤销特定设备的Token） */
    private String deviceId;

    /** 是否撤销所有Token（默认false，只撤销当前Token） */
    private Boolean revokeAll = false;
}
