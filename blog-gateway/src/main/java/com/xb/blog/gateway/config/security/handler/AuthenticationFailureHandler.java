package com.xb.blog.gateway.config.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xb.blog.common.core.constants.Result;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.io.IOException;

/**
 * 自定义认证失败处理器
 */
@Component
public class AuthenticationFailureHandler implements ServerAuthenticationFailureHandler {

    @SneakyThrows(IOException.class)
    @Override
    public Mono<Void> onAuthenticationFailure(WebFilterExchange webFilterExchange, AuthenticationException exception) {
        ServerHttpResponse response = webFilterExchange.getExchange().getResponse();
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        Class exceptionClass = exception.getClass();
        String exceptionMessage = exception.getMessage();

        //用户不存在或密码不正确时 整合错误信息
        if (exceptionClass == UsernameNotFoundException.class || exceptionClass == BadCredentialsException.class) {
            exceptionMessage = "用户名不存在或密码错误！";
        }

        Result result = new Result();
        result.setCode("1");
        result.setMessage("登录失败：" + exceptionMessage);

        byte[] bytes = new ObjectMapper().writeValueAsBytes(result);
        return response.writeWith(Mono.fromSupplier(() -> response.bufferFactory().wrap(bytes)));
    }
}
