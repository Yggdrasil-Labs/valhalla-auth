package com.yggdrasil.labs.client.dto.co;

import java.time.LocalDateTime;

import com.alibaba.cola.dto.DTO;
import com.yggdrasil.labs.client.dto.enums.TokenTypeEnum;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Token 信息对象
 *
 * @author YoungerYang-Y
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TokenCO extends DTO {

    /** 访问令牌 */
    private String accessToken;

    /** 刷新令牌 */
    private String refreshToken;

    /** Token类型 */
    private TokenTypeEnum tokenType;

    /** 过期时间（秒） */
    private Long expiresIn;

    /** 过期时间点 */
    private LocalDateTime expiresAt;

    /** 签发时间 */
    private LocalDateTime issuedAt;

    /** 设备ID */
    private String deviceId;

    /** 设备类型 */
    private String deviceType;
}
