package com.yggdrasil.labs.client.dto.cmd;

import java.io.Serializable;

import lombok.Data;

/**
 * Token 验证命令（RPC 接口用）
 *
 * <p>供外部服务通过 Dubbo RPC 调用验证 Token
 *
 * @author YoungerYang-Y
 */
@Data
public class RpcVerifyTokenCmd implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Token 值 */
    private String token;
}
