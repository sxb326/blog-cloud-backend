package com.xb.blog.common.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.JWTUtil;

public class AuthUtil {

    private static String KEY = "KEY20240421";

    public static Boolean isLogin(String token) {
        if (StrUtil.isBlank(token)) return false;

        return JWTUtil.verify(token, KEY.getBytes());
    }
}
