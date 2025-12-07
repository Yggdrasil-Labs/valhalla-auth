package com.yggdrasil.labs.domain.auth.model.enums;

import lombok.Getter;

/**
 * 用户状态枚举
 *
 * @author YoungerYang-Y
 */
@Getter
public enum UserStatus {

    /** 正常 */
    NORMAL(1, "正常"),

    /** 锁定 */
    LOCKED(2, "锁定"),

    /** 禁用 */
    DISABLED(3, "禁用"),

    /** 过期 */
    EXPIRED(4, "过期");

    private final Integer code;
    private final String desc;

    UserStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /** 根据代码获取枚举 */
    public static UserStatus fromCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (UserStatus status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }

    /** 是否可用（正常状态） */
    public boolean isAvailable() {
        return this == NORMAL;
    }

    /** 是否被锁定 */
    public boolean isLocked() {
        return this == LOCKED;
    }
}
