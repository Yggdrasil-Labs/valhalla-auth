package com.yggdrasil.labs.domain.auth.model.enums;

import lombok.Getter;

/**
 * 密码状态枚举
 *
 * @author YoungerYang-Y
 */
@Getter
public enum PasswordStatus {

    /** 有效 */
    VALID(1, "有效"),

    /** 已过期 */
    EXPIRED(2, "已过期"),

    /** 需重置 */
    NEED_RESET(3, "需重置"),

    /** 临时密码 */
    TEMPORARY(4, "临时密码");

    private final Integer code;
    private final String desc;

    PasswordStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /** 根据代码获取枚举 */
    public static PasswordStatus fromCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (PasswordStatus status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }
}
