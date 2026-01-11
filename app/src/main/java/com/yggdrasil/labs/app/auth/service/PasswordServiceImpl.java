package com.yggdrasil.labs.app.auth.service;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

/**
 * 密码服务实现
 *
 * <p>使用 BCrypt 进行密码加密和验证
 *
 * @author YoungerYang-Y
 */
@Service
public class PasswordServiceImpl implements PasswordService {

    /** BCrypt 加密轮数（成本因子），值越大越安全但越慢，推荐 10-12 */
    private static final int BCRYPT_ROUNDS = 10;

    @Override
    public String encode(String rawPassword) {
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt(BCRYPT_ROUNDS));
    }

    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        try {
            return BCrypt.checkpw(rawPassword, encodedPassword);
        } catch (Exception e) {
            // BCrypt 验证失败时可能抛出异常，返回 false
            return false;
        }
    }
}
