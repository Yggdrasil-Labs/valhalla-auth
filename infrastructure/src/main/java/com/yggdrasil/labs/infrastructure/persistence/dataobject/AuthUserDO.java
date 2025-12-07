package com.yggdrasil.labs.infrastructure.persistence.dataobject;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yggdrasil.labs.mybatis.annotation.AutoMybatis;

import lombok.Data;

/**
 * 用户认证表数据对象
 *
 * <p>对应数据库表：auth_user
 *
 * @author YoungerYang-Y
 */
@Data
@TableName("auth_user")
@AutoMybatis
public class AuthUserDO {

    /** 用户ID（主键，关联用户服务，全局唯一，雪花ID） */
    @TableId(type = IdType.INPUT)
    private Long userId;

    /** 密码哈希值（BCrypt，三方登录可为空） */
    private String passwordHash;

    /** 密码最后修改时间 */
    private LocalDateTime passwordChangedTime;

    /** 最后登录时间 */
    private LocalDateTime lastLoginTime;

    /** 最后登录IP */
    private String lastLoginIp;

    /** 状态：1-正常, 2-锁定, 3-禁用, 4-过期 */
    private Integer status;

    /** 锁定截止时间 */
    private LocalDateTime lockedUntil;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;

    /** 软删除标记：0-未删除, >0-删除时间戳 */
    @TableLogic(value = "0", delval = "CURRENT_TIMESTAMP")
    private Long deletedAt;
}
