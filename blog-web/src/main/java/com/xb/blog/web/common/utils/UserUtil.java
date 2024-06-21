package com.xb.blog.web.common.utils;

/**
 * 自定义的上下文类，使用ThreadLocal来保存每个线程访问时的userId
 */
public class UserUtil {
    private static final ThreadLocal<String> USER_ID = new ThreadLocal<>();

    public static void setUserId(String userId) {
        USER_ID.set(userId);
    }

    public static String getUserId() {
        return USER_ID.get();
    }

    public static void clear() {
        USER_ID.remove();
    }
}
