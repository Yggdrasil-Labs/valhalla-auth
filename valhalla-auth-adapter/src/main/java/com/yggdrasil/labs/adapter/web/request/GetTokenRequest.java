package com.yggdrasil.labs.adapter.web.request;

import lombok.Data;

/**
 * 查询 Token 请求
 *
 * <p>用于路径参数绑定
 *
 * @author YoungerYang-Y
 */
@Data
public class GetTokenRequest {

    /** Token哈希值（可选） */
    private String tokenHash;

    /** JWT ID（可选） */
    private String jwtId;

    /** 用户ID（可选） */
    private Long userId;
}
