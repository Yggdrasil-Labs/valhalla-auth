package com.yggdrasil.labs.client.dto.cmd;

import jakarta.validation.constraints.NotNull;

import com.alibaba.cola.dto.Command;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 禁用账户命令
 *
 * @author YoungerYang-Y
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DisableUserCmd extends Command {

    /** 用户ID */
    @NotNull(message = "用户ID不能为空")
    private Long userId;

    /** 禁用原因（可选） */
    private String reason;
}
