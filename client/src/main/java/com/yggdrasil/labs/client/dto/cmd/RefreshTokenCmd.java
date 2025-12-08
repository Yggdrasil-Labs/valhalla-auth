package com.yggdrasil.labs.client.dto.cmd;

import jakarta.validation.constraints.NotBlank;

import com.alibaba.cola.dto.Command;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Token 刷新命令
 *
 * @author YoungerYang-Y
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RefreshTokenCmd extends Command {

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

