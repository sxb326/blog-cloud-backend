package com.xb.blog.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 认证过滤器：
 * 将这个过滤器配置在 NettyRoutingFilter 之前，实现在路由转发之前进行登录校验工作
 */
@Component
public class AuthFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //校验通过
        return chain.filter(exchange);
        //校验失败
//        ServerHttpResponse response = exchange.getResponse();
//        response.setRawStatusCode(200);
//        return response.setComplete();
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
