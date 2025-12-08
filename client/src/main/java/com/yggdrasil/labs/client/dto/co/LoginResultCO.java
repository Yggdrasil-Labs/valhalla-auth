package com.yggdrasil.labs.client.dto.co;

import com.alibaba.cola.dto.DTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 登录结果对象
 *
 * <p>包含 Token 和用户信息
 *
 * @author YoungerYang-Y
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LoginResultCO extends DTO {

    /** Token 信息 */
    private TokenCO token;

    /** 用户认证信息 */
    private AuthUserCO user;
}
