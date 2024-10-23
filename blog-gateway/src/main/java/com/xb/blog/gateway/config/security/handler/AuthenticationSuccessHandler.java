package com.xb.blog.gateway.config.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xb.blog.common.core.constants.Result;
import com.xb.blog.common.core.utils.AuthUtil;
import com.xb.blog.gateway.pojo.AuthUser;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.io.IOException;

/**
 * 自定义认证成功处理器
 */
@Component
public class AuthenticationSuccessHandler implements ServerAuthenticationSuccessHandler {

    @SneakyThrows(IOException.class)
    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        ServerHttpResponse response = webFilterExchange.getExchange().getResponse();
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        AuthUser user = (AuthUser) authentication.getPrincipal();
        String token = AuthUtil.createToken(user.getId(), user.getUsername());
        response.getHeaders().add("Token", token);
        response.getHeaders().add("Access-Control-Expose-Headers", "Token");

        Result result = new Result();
        result.setCode("0");
        result.setMessage("登录成功");

        byte[] bytes = new ObjectMapper().writeValueAsBytes(result);
        return response.writeWith(Mono.fromSupplier(() -> response.bufferFactory().wrap(bytes)));
    }
}
