package com.yggdrasil.labs.client.dto.query;

import jakarta.validation.constraints.NotNull;

import com.alibaba.cola.dto.Query;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 查询用户信息
 *
 * @author YoungerYang-Y
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GetUserQuery extends Query {

    /** 用户ID */
    @NotNull(message = "用户ID不能为空")
    private Long userId;
}

