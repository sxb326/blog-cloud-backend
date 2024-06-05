package com.xb.blog.common.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.JWTUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 封装认证相关操作
 */
public class AuthUtil {

    private static String KEY = "KEY20240421";

    /**
     * 判断当前请求是否认证
     *
     * @param token
     * @return
     */
    public static Boolean isAuth(String token) {
        return JWTUtil.verify(token, KEY.getBytes());
    }

    /**
     * 根据username生成token
     *
     * @param username
     * @return
     */
    public static String createToken(String username) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("username", username);
        return JWTUtil.createToken(payload, KEY.getBytes());
    }
}
