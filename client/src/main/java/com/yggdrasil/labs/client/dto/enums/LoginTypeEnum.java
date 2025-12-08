package com.yggdrasil.labs.client.dto.enums;

import lombok.Getter;

/**
 * 登录类型枚举
 *
 * <p>对应 Domain 层的 {@link com.yggdrasil.labs.domain.auth.model.enums.LoginType}
 *
 * @author YoungerYang-Y
 */
@Getter
public enum LoginTypeEnum {

    /** 密码登录 */
    PASSWORD(1, "密码登录"),

    /** 微信登录 */
    WECHAT(2, "微信登录"),

    /** Google登录 */
    GOOGLE(3, "Google登录"),

    /** 令牌登录 */
    TOKEN(4, "令牌登录"),

    /** 其他 */
    OTHER(5, "其他");

    private final Integer code;
    private final String desc;

    LoginTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /** 根据代码获取枚举 */
    public static LoginTypeEnum fromCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (LoginTypeEnum type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }
}
