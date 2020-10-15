package com.paulok777.controller.util;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5PasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(String password) {
        return DigestUtils.md5Hex(password);
    }
}
