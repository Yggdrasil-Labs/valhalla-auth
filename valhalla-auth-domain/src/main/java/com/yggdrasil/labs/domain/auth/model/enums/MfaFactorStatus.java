package com.yggdrasil.labs.domain.auth.model.enums;

import lombok.Getter;

/**
 * MFA 因子状态枚举
 *
 * @author YoungerYang-Y
 */
@Getter
public enum MfaFactorStatus {

    /** 启用 */
    ENABLED(1, "启用"),

    /** 禁用 */
    DISABLED(2, "禁用");

    private final Integer code;
    private final String desc;

    MfaFactorStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /** 根据代码获取枚举 */
    public static MfaFactorStatus fromCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (MfaFactorStatus status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }
}
