package com.xb.blog.web.common.handler;

import com.xb.blog.common.constants.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理类
 */
@RestControllerAdvice(value = "com.xb.blog.web.controller")
public class CustomExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result exception() {
        return Result.error("系统异常！请稍后再试！");
    }
}
