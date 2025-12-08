package com.yggdrasil.labs.client.dto.enums;

import lombok.Getter;

/**
 * Token类型枚举
 *
 * <p>对应 Domain 层的 {@link com.yggdrasil.labs.domain.auth.model.enums.TokenType}
 *
 * @author YoungerYang-Y
 */
@Getter
public enum TokenTypeEnum {

    /** 访问令牌 */
    ACCESS(1, "访问令牌"),

    /** 刷新令牌 */
    REFRESH(2, "刷新令牌");

    private final Integer code;
    private final String desc;

    TokenTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /** 根据代码获取枚举 */
    public static TokenTypeEnum fromCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (TokenTypeEnum type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }
}
