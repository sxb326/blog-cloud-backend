package com.xb.blog.common.core.constants;

public enum ResultEnum {

    NO_LOGIN("401", "未登录"),
    NO_PERMISSION("403", "权限不足"),
    NO_EXISTS("404", "资源不存在");

    ResultEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    private String code;
    private String message;


    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
