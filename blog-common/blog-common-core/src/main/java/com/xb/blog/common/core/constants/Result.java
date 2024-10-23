package com.xb.blog.common.core.constants;

import lombok.Data;

import java.io.Serializable;

/**
 * 全局返回对象
 */
@Data
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private String code;
    private String message;
    private T data;

    public static Result success() {
        return success("操作成功");
    }

    public static Result success(String message) {
        return success(message, null);
    }

    public static Result success(Object data) {
        return success("success", data);
    }

    public static Result success(String message, Object data) {
        Result result = new Result();
        result.setCode("0");
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    public static Result redirect(String message) {
        Result result = new Result();
        result.setCode("302");
        result.setMessage(message);
        return result;
    }

    public static Result error(String message) {
        Result result = new Result();
        result.setCode("500");
        result.setMessage(message);
        return result;
    }
}
