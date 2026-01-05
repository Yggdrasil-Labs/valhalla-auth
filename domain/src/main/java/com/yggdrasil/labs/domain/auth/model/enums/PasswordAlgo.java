package com.yggdrasil.labs.domain.auth.model.enums;

import lombok.Getter;

/**
 * 密码算法枚举
 *
 * @author YoungerYang-Y
 */
@Getter
public enum PasswordAlgo {

    /** BCrypt */
    BCRYPT(1, "BCrypt"),

    /** Argon2id */
    ARGON2ID(2, "Argon2id"),

    /** PBKDF2 */
    PBKDF2(3, "PBKDF2");

    private final Integer code;
    private final String desc;

    PasswordAlgo(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /** 根据代码获取枚举 */
    public static PasswordAlgo fromCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (PasswordAlgo algo : values()) {
            if (algo.getCode().equals(code)) {
                return algo;
            }
        }
        return null;
    }
}
