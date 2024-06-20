package com.xb.blog.common.constants;

import lombok.Data;

import java.io.Serializable;

/**
 * 全局返回对象
 */
@Data
public class Result implements Serializable {
    private static final long serialVersionUID = 1L;

    private String code;
    private String message;
    private Object data;

    public static Result success(Object data) {
        Result result = new Result();
        result.setCode("0");
        result.setMessage("success");
        result.setData(data);
        return result;
    }

    public static Result error(String message) {
        Result result = new Result();
        result.setCode("500");
        result.setMessage(message);
        return result;
    }
}
