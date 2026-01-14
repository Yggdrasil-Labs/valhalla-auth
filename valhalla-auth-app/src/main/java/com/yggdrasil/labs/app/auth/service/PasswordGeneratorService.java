package com.yggdrasil.labs.app.auth.service;

/**
 * 密码生成服务接口
 *
 * <p>负责生成安全的默认密码
 *
 * @author YoungerYang-Y
 */
public interface PasswordGeneratorService {

    /**
     * 生成默认密码
     *
     * <p>生成 12 位随机密码，包含大小写字母、数字、特殊字符
     *
     * @return 生成的密码
     */
    String generateDefaultPassword();
}
