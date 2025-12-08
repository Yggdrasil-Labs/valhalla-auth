package com.yggdrasil.labs.client.dto.cmd;

import jakarta.validation.constraints.NotNull;

import com.alibaba.cola.dto.Command;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 删除凭证命令
 *
 * @author YoungerYang-Y
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DeleteCredentialCmd extends Command {

    /** 凭证ID */
    @NotNull(message = "凭证ID不能为空")
    private Long credentialId;
}
