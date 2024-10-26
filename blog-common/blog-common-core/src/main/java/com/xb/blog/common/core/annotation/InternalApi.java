package com.xb.blog.common.core.annotation;

import java.lang.annotation.*;

/**
 * 自定义注解，标注了该注解的接口 仅允许内部服务调用 不允许直接外部调用
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface InternalApi {
}