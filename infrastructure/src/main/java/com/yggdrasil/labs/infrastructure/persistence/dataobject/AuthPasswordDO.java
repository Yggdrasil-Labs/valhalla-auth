package com.yggdrasil.labs.infrastructure.persistence.dataobject;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yggdrasil.labs.mybatis.annotation.AutoMybatis;

import lombok.Data;

/**
 * 用户密码表数据对象
 *
 * <p>对应数据库表：auth_password
 *
 * @author YoungerYang-Y
 */
@Data
@TableName("auth_password")
@AutoMybatis
public class AuthPasswordDO {

    /** 用户ID（主键，关联用户服务，全局唯一） */
    @TableId(type = IdType.INPUT)
    private Long userId;

    /** 密码哈希值（BCrypt等） */
    private String passwordHash;

    /** 密码算法：1-BCrypt, 2-Argon2id, 3-PBKDF2 */
    private Integer passwordAlgo;

    /** 密码策略版本（用于密码升级） */
    private Integer passwordVersion;

    /** 密码状态：1-有效, 2-已过期, 3-需重置, 4-临时密码 */
    private Integer passwordStatus;

    /** 是否强制改密：0-否, 1-是 */
    private Boolean forceChange;

    /** 密码过期时间 */
    private LocalDateTime passwordExpiresAt;

    /** 最后修改时间 */
    private LocalDateTime changedAt;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;
}
