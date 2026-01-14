package com.yggdrasil.labs.app.auth.service;

/**
 * 密码服务接口
 *
 * <p>负责密码加密和验证
 *
 * @author YoungerYang-Y
 */
public interface PasswordService {

    /**
     * 加密密码
     *
     * @param rawPassword 明文密码
     * @return 加密后的密码哈希值
     */
    String encode(String rawPassword);

    /**
     * 验证密码
     *
     * @param rawPassword 明文密码
     * @param encodedPassword 加密后的密码哈希值
     * @return 是否匹配
     */
    boolean matches(String rawPassword, String encodedPassword);
}
