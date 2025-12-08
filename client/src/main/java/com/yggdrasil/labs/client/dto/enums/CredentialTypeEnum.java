package com.yggdrasil.labs.client.dto.enums;

import lombok.Getter;

/**
 * 凭证类型枚举
 *
 * <p>对应 Domain 层的 {@link com.yggdrasil.labs.domain.auth.model.enums.CredentialType}
 *
 * @author YoungerYang-Y
 */
@Getter
public enum CredentialTypeEnum {

    /** 用户名 */
    USERNAME(1, "用户名"),

    /** 手机号 */
    PHONE(2, "手机号"),

    /** 邮箱 */
    EMAIL(3, "邮箱"),

    /** 微信 */
    WECHAT(4, "微信"),

    /** Google */
    GOOGLE(5, "Google"),

    /** 其他三方 */
    OTHER(6, "其他三方");

    private final Integer code;
    private final String desc;

    CredentialTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /** 根据代码获取枚举 */
    public static CredentialTypeEnum fromCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (CredentialTypeEnum type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }

    /** 是否为三方登录 */
    public boolean isThirdParty() {
        return this == WECHAT || this == GOOGLE || this == OTHER;
    }
}
