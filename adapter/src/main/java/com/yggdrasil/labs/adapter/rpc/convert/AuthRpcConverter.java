package com.yggdrasil.labs.adapter.rpc.convert;

import org.springframework.stereotype.Component;

import com.yggdrasil.labs.app.auth.dto.cmd.CreateCredentialCmd;
import com.yggdrasil.labs.app.auth.dto.cmd.InitializeUserCmd;
import com.yggdrasil.labs.app.auth.dto.co.UserInitializationCO;
import com.yggdrasil.labs.app.auth.dto.enums.CredentialTypeEnum;
import com.yggdrasil.labs.client.dto.cmd.RpcCreateCredentialCmd;
import com.yggdrasil.labs.client.dto.cmd.RpcInitializeUserCmd;
import com.yggdrasil.labs.client.dto.co.RpcUserInitializationCO;
import com.yggdrasil.labs.client.dto.enums.RpcCredentialTypeEnum;

/**
 * RPC 层 DTO 转换器
 *
 * <p>负责 Client 层 DTO 与 App 层 DTO 之间的转换
 *
 * @author YoungerYang-Y
 */
@Component
public class AuthRpcConverter {

    /** RpcCreateCredentialCmd -> CreateCredentialCmd */
    public CreateCredentialCmd toAppCmd(RpcCreateCredentialCmd src) {
        if (src == null) {
            return null;
        }
        var dest = new CreateCredentialCmd();
        dest.setUserId(src.getUserId());
        dest.setCredentialType(toAppEnum(src.getCredentialType()));
        dest.setCredentialValue(src.getCredentialValue());
        dest.setThirdPartyId(src.getThirdPartyId());
        dest.setThirdPartyName(src.getThirdPartyName());
        dest.setIsPrimary(src.getIsPrimary());
        return dest;
    }

    /** RpcInitializeUserCmd -> InitializeUserCmd */
    public InitializeUserCmd toAppCmd(RpcInitializeUserCmd src) {
        if (src == null) {
            return null;
        }
        var dest = new InitializeUserCmd();
        dest.setUserId(src.getUserId());
        dest.setCredentialType(toAppEnum(src.getCredentialType()));
        dest.setCredentialValue(src.getCredentialValue());
        dest.setInitialPassword(src.getInitialPassword());
        dest.setProvider(src.getProvider());
        dest.setVerified(src.getVerified());
        dest.setIsPrimary(src.getIsPrimary());
        return dest;
    }

    /** UserInitializationCO -> RpcUserInitializationCO */
    public RpcUserInitializationCO toRpcCO(UserInitializationCO src) {
        if (src == null) {
            return null;
        }
        var dest = new RpcUserInitializationCO();
        dest.setCredentialId(src.getCredentialId());
        dest.setCredentialType(toRpcEnum(src.getCredentialType()));
        dest.setCredentialValue(src.getCredentialValue());
        dest.setProvider(src.getProvider());
        dest.setInitialPassword(src.getInitialPassword());
        dest.setForceChangePassword(src.getForceChangePassword());
        return dest;
    }

    /** RpcCredentialTypeEnum -> CredentialTypeEnum */
    public CredentialTypeEnum toAppEnum(RpcCredentialTypeEnum src) {
        if (src == null) {
            return null;
        }
        return CredentialTypeEnum.fromCode(src.getCode());
    }

    /** CredentialTypeEnum -> RpcCredentialTypeEnum */
    public RpcCredentialTypeEnum toRpcEnum(CredentialTypeEnum src) {
        if (src == null) {
            return null;
        }
        return RpcCredentialTypeEnum.fromCode(src.getCode());
    }
}
