package com.yggdrasil.labs.app.auth.service;

import java.security.SecureRandom;

import org.springframework.stereotype.Service;

/**
 * 密码生成服务实现
 *
 * <p>使用 SecureRandom 生成安全的默认密码
 *
 * @author YoungerYang-Y
 */
@Service
public class PasswordGeneratorServiceImpl implements PasswordGeneratorService {

    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL = "!@#$%^&*()_+-=[]{}|;:,.<>?";
    private static final String ALL_CHARS = UPPERCASE + LOWERCASE + DIGITS + SPECIAL;

    private static final int PASSWORD_LENGTH = 12;
    private static final SecureRandom RANDOM = new SecureRandom();

    @Override
    public String generateDefaultPassword() {
        StringBuilder password = new StringBuilder(PASSWORD_LENGTH);

        // 确保至少包含一个大写字母
        password.append(UPPERCASE.charAt(RANDOM.nextInt(UPPERCASE.length())));
        // 确保至少包含一个小写字母
        password.append(LOWERCASE.charAt(RANDOM.nextInt(LOWERCASE.length())));
        // 确保至少包含一个数字
        password.append(DIGITS.charAt(RANDOM.nextInt(DIGITS.length())));
        // 确保至少包含一个特殊字符
        password.append(SPECIAL.charAt(RANDOM.nextInt(SPECIAL.length())));

        // 填充剩余字符
        for (int i = password.length(); i < PASSWORD_LENGTH; i++) {
            password.append(ALL_CHARS.charAt(RANDOM.nextInt(ALL_CHARS.length())));
        }

        // 打乱字符顺序
        char[] passwordArray = password.toString().toCharArray();
        for (int i = passwordArray.length - 1; i > 0; i--) {
            int j = RANDOM.nextInt(i + 1);
            char temp = passwordArray[i];
            passwordArray[i] = passwordArray[j];
            passwordArray[j] = temp;
        }

        return new String(passwordArray);
    }
}
