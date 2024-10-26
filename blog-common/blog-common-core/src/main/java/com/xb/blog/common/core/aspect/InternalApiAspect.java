package com.xb.blog.common.core.aspect;

import cn.hutool.core.util.StrUtil;
import com.xb.blog.common.core.annotation.InternalApi;
import com.xb.blog.common.core.exception.InternalApiIllegalCallException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 拦截 @Inner 注解的切面，用于保证该注解标注的接口只能内部服务调用
 */
@Aspect
public class InternalApiAspect {

    @Before("@annotation(internalApi)")
    public void before(JoinPoint joinPoint, InternalApi internalApi) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        System.out.println(request.getRequestURI());
        System.out.println(request.getHeader("Token"));
        //受保护的接口 一般都是登录后才可访问。如果未登录，自然会被拦截。如果登录了，这里抛出异常。因为这是内部api
        String token = request.getHeader("Token");
        if (StrUtil.isNotBlank(token)) {
            throw new InternalApiIllegalCallException("非法请求！");
        }
    }
}