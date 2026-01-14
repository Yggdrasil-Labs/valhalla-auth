package com.yggdrasil.labs.domain.auth.model.enums;

import lombok.Getter;

/**
 * 登录状态枚举
 *
 * @author YoungerYang-Y
 */
@Getter
public enum LoginStatus {

    /** 失败 */
    FAILED(0, "失败"),

    /** 成功 */
    SUCCESS(1, "成功");

    private final Integer code;
    private final String desc;

    LoginStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /** 根据代码获取枚举 */
    public static LoginStatus fromCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (LoginStatus status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }

    /** 是否成功 */
    public boolean isSuccess() {
        return this == SUCCESS;
    }
}
