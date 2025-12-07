package com.yggdrasil.labs.infrastructure.persistence.dataobject;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yggdrasil.labs.mybatis.annotation.AutoMybatis;

import lombok.Data;

/**
 * Token记录表数据对象
 *
 * <p>对应数据库表：auth_token
 *
 * <p>存储 Token 信息（用于审计和追踪，主要存储于 Redis）
 *
 * @author YoungerYang-Y
 */
@Data
@TableName("auth_token")
@AutoMybatis
public class AuthTokenDO {

    /** Token记录ID（主键） */
    @TableId(type = IdType.AUTO)
    private Long tokenId;

    /** 用户ID（关联用户服务，全局唯一） */
    private Long userId;

    /** Token类型：1-访问令牌(ACCESS), 2-刷新令牌(REFRESH) */
    private Integer tokenType;

    /** Token哈希值（用于查询，不存储完整Token） */
    private String tokenHash;

    /** JWT ID（JTI） */
    private String jwtId;

    /** 设备ID */
    private String deviceId;

    /** 设备类型：WEB, MOBILE, API */
    private String deviceType;

    /** 设备名称 */
    private String deviceName;

    /** IP地址 */
    private String ipAddress;

    /** 用户代理 */
    private String userAgent;

    /** 登录地点（根据IP解析） */
    private String location;

    /** 签发时间 */
    private LocalDateTime issuedAt;

    /** 过期时间 */
    private LocalDateTime expiresAt;

    /** 最后使用时间 */
    private LocalDateTime lastUsedAt;

    /** 是否已撤销：0-未撤销, 1-已撤销 */
    private Boolean revoked;

    /** 撤销时间 */
    private LocalDateTime revokedAt;

    /** 撤销原因 */
    private String revokeReason;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;
}
