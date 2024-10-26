package com.xb.blog.common.core.handler;

import com.xb.blog.common.core.constants.Result;
import com.xb.blog.common.core.exception.InternalApiIllegalCallException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理类
 */
@Slf4j
@RestControllerAdvice(value = "com.xb.blog")
public class CustomExceptionHandler {

    @ExceptionHandler(InternalApiIllegalCallException.class)
    public Result internalApiIllegalCallException(Exception e) {
        log.error("{}", e.getMessage(), e);
        return Result.error(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Result exception(Exception e) {
        log.error("{}", e.getMessage(), e);
        return Result.error("系统异常！请稍后再试！");
    }
}