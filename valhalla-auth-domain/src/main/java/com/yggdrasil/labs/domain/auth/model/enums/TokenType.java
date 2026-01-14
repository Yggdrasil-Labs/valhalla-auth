package com.yggdrasil.labs.domain.auth.model.enums;

import lombok.Getter;

/**
 * Token类型枚举
 *
 * @author YoungerYang-Y
 */
@Getter
public enum TokenType {

    /** 访问令牌 */
    ACCESS(1, "访问令牌"),

    /** 刷新令牌 */
    REFRESH(2, "刷新令牌");

    private final Integer code;
    private final String desc;

    TokenType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /** 根据代码获取枚举 */
    public static TokenType fromCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (TokenType type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }
}
