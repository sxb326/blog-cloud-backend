package com.xb.blog.gateway.config.security.converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.server.authentication.ServerFormLoginAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * 自定义登录参数转换器 从post请求体body中获取用户名密码
 */
@Component
public class AuthenticationConverter extends ServerFormLoginAuthenticationConverter {

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();

        return DataBufferUtils.join(request.getBody())
                .flatMap(dataBuffer -> {
                    byte[] bytes = new byte[dataBuffer.readableByteCount()];
                    dataBuffer.read(bytes);
                    DataBufferUtils.release(dataBuffer);

                    String body = new String(bytes, StandardCharsets.UTF_8);

                    try {
                        //解析json 获取用户名 密码
                        JsonNode node = new ObjectMapper().readTree(body);
                        String username = node.get("username").asText();
                        String password = node.get("password").asText();

                        //创建认证token
                        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password, AuthorityUtils.NO_AUTHORITIES);

                        return Mono.just(token);
                    } catch (Exception e) {
                        return Mono.error(e);
                    }
                });
    }
}
