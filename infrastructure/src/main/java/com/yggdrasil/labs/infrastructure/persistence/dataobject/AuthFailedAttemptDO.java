package com.yggdrasil.labs.infrastructure.persistence.dataobject;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yggdrasil.labs.mybatis.annotation.AutoMybatis;

import lombok.Data;

/**
 * 失败尝试表数据对象（防爆破）
 *
 * <p>对应数据库表：auth_failed_attempt
 *
 * <p>记录登录失败尝试，用于防止暴力破解攻击
 *
 * @author YoungerYang-Y
 */
@Data
@TableName("auth_failed_attempt")
@AutoMybatis
public class AuthFailedAttemptDO {

    /** 尝试ID（主键） */
    @TableId(type = IdType.AUTO)
    private Long attemptId;

    /** 用户ID（可为空） */
    private Long userId;

    /** 凭证类型：1-用户名, 2-手机号, 3-邮箱, 4-微信, 5-Google, 6-其他 */
    private Integer credentialType;

    /** 尝试的凭证值（用户名/手机号/邮箱等） */
    private String credentialValue;

    /** IP地址 */
    private String ipAddress;

    /** 用户代理 */
    private String userAgent;

    /** 设备指纹 */
    private String deviceFingerprint;

    /** 失败原因 */
    private String failReason;

    /** 尝试时间 */
    private LocalDateTime attemptTime;
}
