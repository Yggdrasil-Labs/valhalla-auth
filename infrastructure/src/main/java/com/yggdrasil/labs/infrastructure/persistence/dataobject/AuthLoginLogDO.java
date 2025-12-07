package com.yggdrasil.labs.infrastructure.persistence.dataobject;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yggdrasil.labs.mybatis.annotation.AutoMybatis;

import lombok.Data;

/**
 * 登录日志表数据对象
 *
 * <p>对应数据库表：auth_login_log
 *
 * <p>记录所有登录尝试（成功/失败）
 *
 * @author YoungerYang-Y
 */
@Data
@TableName("auth_login_log")
@AutoMybatis
public class AuthLoginLogDO {

    /** 日志ID（主键） */
    @TableId(type = IdType.AUTO)
    private Long logId;

    /** 用户ID（关联用户服务，全局唯一） */
    private Long userId;

    /** 登录凭证类型：1-用户名, 2-手机号, 3-邮箱, 4-微信, 5-Google, 6-其他 */
    private Integer credentialType;

    /** 登录凭证值（脱敏处理） */
    private String credentialValue;

    /** 登录类型：1-密码登录(PASSWORD), 2-微信登录(WECHAT), 3-Google登录(GOOGLE), 4-令牌登录(TOKEN), 5-其他 */
    private Integer loginType;

    /** 登录状态：1-成功(SUCCESS), 0-失败(FAILED) */
    private Integer loginStatus;

    /** 是否使用了MFA：0-否, 1-是 */
    private Boolean mfaUsed;

    /** 使用的MFA类型：1-TOTP, 2-短信, 3-邮箱, 4-U2F */
    private Integer mfaType;

    /** 失败原因 */
    private String failReason;

    /** 登录IP */
    private String loginIp;

    /** 用户代理（User-Agent） */
    private String userAgent;

    /** 设备类型：WEB, MOBILE, API */
    private String deviceType;

    /** 设备ID */
    private String deviceId;

    /** 登录地点（根据IP解析） */
    private String location;

    /** 登录时间 */
    private LocalDateTime loginTime;
}
