package com.xb.blog.common.core.utils;

import cn.hutool.jwt.JWT;
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
    public static String createToken(String id, String username) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("id", id);
        payload.put("username", username);
        return JWTUtil.createToken(payload, KEY.getBytes());
    }

    /**
     * 解析token 获取username
     *
     * @param token
     * @return
     */
    public static String getLoginUsername(String token) {
        JWT jwt = JWTUtil.parseToken(token);
        return jwt.getPayload("username").toString();
    }

    /**
     * 获取登录用户的id
     *
     * @param token
     * @return
     */
    public static String getLoginId(String token) {
        JWT jwt = JWTUtil.parseToken(token);
        return jwt.getPayload("id").toString();
    }
}
