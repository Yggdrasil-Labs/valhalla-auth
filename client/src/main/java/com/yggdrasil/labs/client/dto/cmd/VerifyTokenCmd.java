package com.yggdrasil.labs.client.dto.cmd;

import jakarta.validation.constraints.NotBlank;

import com.alibaba.cola.dto.Command;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Token 验证命令
 *
 * @author YoungerYang-Y
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class VerifyTokenCmd extends Command {

    /** Token值 */
    @NotBlank(message = "Token不能为空")
    private String token;
}
