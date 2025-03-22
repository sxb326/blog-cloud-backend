package com.xb.blog.gateway.config.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xb.blog.common.core.constants.Result;
import com.xb.blog.common.core.constants.ResultEnum;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.IOException;

/**
 * 自定义未认证拦截处理器
 */
@Component
public class AuthenticationEntryPoint implements ServerAuthenticationEntryPoint {

    @SneakyThrows(IOException.class)
    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException ex) {
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        Result result = new Result(ResultEnum.NO_LOGIN);

        byte[] bytes = new ObjectMapper().writeValueAsBytes(result);
        return response.writeWith(Mono.fromSupplier(() -> response.bufferFactory().wrap(bytes)));
    }
}
