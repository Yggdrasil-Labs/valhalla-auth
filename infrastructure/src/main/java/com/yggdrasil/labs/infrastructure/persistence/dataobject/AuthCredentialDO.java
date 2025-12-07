package com.yggdrasil.labs.infrastructure.persistence.dataobject;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yggdrasil.labs.mybatis.annotation.AutoMybatis;

import lombok.Data;

/**
 * 登录凭证表数据对象
 *
 * <p>对应数据库表：auth_credential
 *
 * <p>支持多种登录方式：用户名、手机号、邮箱、三方登录（微信/Google等）
 *
 * @author YoungerYang-Y
 */
@Data
@TableName("auth_credential")
@AutoMybatis
public class AuthCredentialDO {

    /** 凭证ID（主键，雪花ID） */
    @TableId(type = IdType.INPUT)
    private Long credentialId;

    /** 用户ID（关联用户服务，全局唯一） */
    private Long userId;

    /**
     * 凭证类型：1-用户名(USERNAME), 2-手机号(PHONE), 3-邮箱(EMAIL), 4-微信(WECHAT), 5-Google(GOOGLE),
     * 6-其他三方(OTHER)
     */
    private Integer credentialType;

    /** 凭证值（用户名/手机号/邮箱/三方ID） */
    private String credentialValue;

    /** 三方登录的唯一ID（仅三方登录时使用） */
    private String thirdPartyId;

    /** 三方登录名称（如：wechat, google） */
    private String thirdPartyName;

    /** 是否主凭证：0-否, 1-是 */
    private Boolean isPrimary;

    /** 是否已验证：0-未验证, 1-已验证 */
    private Boolean verified;

    /** 验证时间 */
    private LocalDateTime verifiedAt;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;

    /** 软删除标记：0-未删除, >0-删除时间戳 */
    @TableLogic(value = "0", delval = "CURRENT_TIMESTAMP")
    private Long deletedAt;
}
