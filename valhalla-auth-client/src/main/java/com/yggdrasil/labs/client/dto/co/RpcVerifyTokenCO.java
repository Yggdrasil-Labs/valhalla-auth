package com.yggdrasil.labs.client.dto.co;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * Token 验证结果（RPC 接口用）
 *
 * <p>返回给外部服务的 Token 验证结果
 *
 * @author YoungerYang-Y
 */
@Data
public class RpcVerifyTokenCO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 用户ID */
    private Long userId;

    /** 过期时间 */
    private LocalDateTime expiresAt;

    /** 是否降级模式 */
    private Boolean degraded;
}
