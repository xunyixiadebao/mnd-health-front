package org.example.djiankang.common;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.MD5;

/**
 * 密码加密工具类
 * 使用 MD5 + 用户名作为盐值
 */
public class PasswordEncryptor {

    /**
     * 使用用户名作为盐值加密密码
     *
     * @param username    用户名（作为盐值的一部分）
     * @param rawPassword 明文密码
     * @return 加密后的密码（大写）
     */
    public static String encryptWithUsernameSalt(String username, String rawPassword) {
        MD5 md5 = MD5.create();

        // 1. 对用户名进行MD5
        String usernameMd5 = md5.digestHex(username);

        // 2. 取前6位作为盐前缀
        String saltPrefix = StrUtil.subWithLength(usernameMd5, 0, 6);

        // 3. 取后3位作为盐后缀
        String saltSuffix = StrUtil.subSuf(usernameMd5, usernameMd5.length() - 3);

        // 4. 拼接：盐前缀 + 密码 + 盐后缀
        String saltedPassword = saltPrefix + rawPassword + saltSuffix;

        // 5. 最终MD5加密并转大写
        return md5.digestHex(saltedPassword).toUpperCase();
    }
}
