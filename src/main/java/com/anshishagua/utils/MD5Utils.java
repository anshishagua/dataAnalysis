package com.anshishagua.utils;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import java.util.Objects;

/**
 * User: lixiao
 * Date: 2018/5/30
 * Time: 上午10:10
 */

public class MD5Utils {
    private static final String ALGORITHM_NAME = "md5";
    private static final String SALT = "1agqr23xfg";
    private static final int HASH_ITERATIONS = 3;

    public static String encrypt(String password) {
        Objects.requireNonNull(password);

        return new SimpleHash(ALGORITHM_NAME, password, ByteSource.Util.bytes(SALT), HASH_ITERATIONS).toHex();
    }

    public static String encrypt(String username, String password) {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);

        return new SimpleHash(ALGORITHM_NAME, password, ByteSource.Util.bytes(SALT + username), HASH_ITERATIONS).toHex();
    }
}