package com.yggdrasil.labs.client.dto.enums;

import lombok.Getter;

/**
 * 设备类型枚举
 *
 * @author YoungerYang-Y
 */
@Getter
public enum DeviceTypeEnum {

    /** Web 设备 */
    WEB("WEB", "Web设备"),

    /** 移动设备 */
    MOBILE("MOBILE", "移动设备"),

    /** API 调用 */
    API("API", "API调用");

    private final String code;
    private final String desc;

    DeviceTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /** 根据代码获取枚举 */
    public static DeviceTypeEnum fromCode(String code) {
        if (code == null) {
            return null;
        }
        for (DeviceTypeEnum type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }
}

