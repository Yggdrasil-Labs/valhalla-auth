package com.yggdrasil.labs.adapter.web.request;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

/**
 * 查询用户请求
 *
 * <p>用于路径参数绑定
 *
 * @author YoungerYang-Y
 */
@Data
public class GetUserRequest {

    /** 用户ID */
    @NotNull(message = "用户ID不能为空")
    private Long userId;
}
