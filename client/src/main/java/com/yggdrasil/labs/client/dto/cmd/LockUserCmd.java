package com.yggdrasil.labs.client.dto.cmd;

import jakarta.validation.constraints.NotNull;

import com.alibaba.cola.dto.Command;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 锁定账户命令
 *
 * @author YoungerYang-Y
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LockUserCmd extends Command {

    /** 用户ID */
    @NotNull(message = "用户ID不能为空")
    private Long userId;

    /** 锁定时长（分钟，可选，默认30分钟） */
    private Integer lockDurationMinutes = 30;

    /** 锁定原因（可选） */
    private String reason;
}

