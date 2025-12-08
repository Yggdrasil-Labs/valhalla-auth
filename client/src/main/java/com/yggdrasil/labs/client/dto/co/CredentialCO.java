package com.yggdrasil.labs.client.dto.co;

import java.time.LocalDateTime;

import com.alibaba.cola.dto.DTO;
import com.yggdrasil.labs.client.dto.enums.CredentialTypeEnum;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 凭证信息对象
 *
 * @author YoungerYang-Y
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CredentialCO extends DTO {

    /** 凭证ID */
    private Long credentialId;

    /** 用户ID */
    private Long userId;

    /** 凭证类型 */
    private CredentialTypeEnum credentialType;

    /** 凭证值（脱敏处理） */
    private String credentialValue;

    /** 三方登录的唯一ID（仅三方登录时使用） */
    private String thirdPartyId;

    /** 三方登录名称（如：wechat, google） */
    private String thirdPartyName;

    /** 是否主凭证 */
    private Boolean isPrimary;

    /** 是否已验证 */
    private Boolean verified;

    /** 验证时间 */
    private LocalDateTime verifiedAt;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;
}

