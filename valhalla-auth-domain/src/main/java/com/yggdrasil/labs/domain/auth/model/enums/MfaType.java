package com.yggdrasil.labs.domain.auth.model.enums;

import lombok.Getter;

/**
 * MFA类型枚举
 *
 * @author YoungerYang-Y
 */
@Getter
public enum MfaType {

    /** TOTP（时间-based一次性密码） */
    TOTP(1, "TOTP"),

    /** 短信验证码 */
    SMS(2, "短信"),

    /** 邮箱验证码 */
    EMAIL(3, "邮箱"),

    /** U2F */
    U2F(4, "U2F");

    private final Integer code;
    private final String desc;

    MfaType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /** 根据代码获取枚举 */
    public static MfaType fromCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (MfaType type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }
}
