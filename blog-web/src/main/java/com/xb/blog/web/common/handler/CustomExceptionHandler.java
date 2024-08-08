package com.xb.blog.web.common.handler;

import com.xb.blog.common.core.constants.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理类
 */
@Slf4j
@RestControllerAdvice(value = "com.xb.blog.web.controller")
public class CustomExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result exception(Exception e) {
        log.error("{}",e.getMessage());
        return Result.error("系统异常！请稍后再试！");
    }
}
