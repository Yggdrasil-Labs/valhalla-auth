package com.yggdrasil.labs.client.dto.query;

import com.alibaba.cola.dto.Query;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 查询 Token 信息
 *
 * @author YoungerYang-Y
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GetTokenQuery extends Query {

    /** Token哈希值（可选） */
    private String tokenHash;

    /** JWT ID（可选） */
    private String jwtId;

    /** 用户ID（可选） */
    private Long userId;
}

