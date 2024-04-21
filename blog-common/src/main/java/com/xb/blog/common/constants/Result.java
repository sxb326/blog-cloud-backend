package com.xb.blog.common.constants;

import lombok.Data;

import java.io.Serializable;

/**
 * 全局返回对象
 *
 * @param <T>
 */
@Data
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private String code;
    private String message;
    private T data;
}
