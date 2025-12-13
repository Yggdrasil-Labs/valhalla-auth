package com.yggdrasil.labs.adapter.web.request;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

/**
 * 查询凭证列表请求
 *
 * <p>用于路径参数绑定
 *
 * @author YoungerYang-Y
 */
@Data
public class ListCredentialsRequest {

    /** 用户ID */
    @NotNull(message = "用户ID不能为空")
    private Long userId;
}
