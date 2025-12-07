package com.yggdrasil.labs.infrastructure.persistence.dataobject;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yggdrasil.labs.mybatis.annotation.AutoMybatis;

import lombok.Data;

/**
 * 多因子认证表数据对象
 *
 * <p>对应数据库表：auth_mfa
 *
 * <p>支持 TOTP、短信验证码、邮箱验证码、U2F Key 配置
 *
 * @author YoungerYang-Y
 */
@Data
@TableName("auth_mfa")
@AutoMybatis
public class AuthMfaDO {

    /** MFA ID（主键，雪花ID） */
    @TableId(type = IdType.INPUT)
    private Long mfaId;

    /** 用户ID（关联用户服务） */
    private Long userId;

    /** MFA类型：1-TOTP, 2-短信(SMS), 3-邮箱(EMAIL), 4-U2F */
    private Integer mfaType;

    /** MFA名称（如：Google Authenticator、备用手机号等） */
    private String mfaName;

    /** 密钥（TOTP密钥、U2F Key等，加密存储） */
    private String secretKey;

    /** 手机号（用于短信验证） */
    private String phoneNumber;

    /** 邮箱（用于邮箱验证） */
    private String email;

    /** U2F Key Handle */
    private String u2fKeyHandle;

    /** U2F 公钥 */
    private String u2fPublicKey;

    /** 备用验证码（JSON数组，加密存储） */
    private String backupCodes;

    /** 是否启用：0-未启用, 1-已启用 */
    private Boolean isEnabled;

    /** 是否默认MFA方式：0-否, 1-是 */
    private Boolean isDefault;

    /** 最后使用时间 */
    private LocalDateTime lastUsedAt;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;

    /** 软删除标记：0-未删除, >0-删除时间戳 */
    @TableLogic(value = "0", delval = "CURRENT_TIMESTAMP")
    private Long deletedAt;
}
