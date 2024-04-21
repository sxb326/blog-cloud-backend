package com.xb.blog.gateway.filter;

import com.xb.blog.gateway.constants.AuthProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 认证过滤器：
 * 将这个过滤器配置在 NettyRoutingFilter 之前，实现在路由转发之前进行登录校验工作
 */
@Component
public class AuthFilter implements GlobalFilter, Ordered {

    @Autowired
    private AuthProperties authProperties;

    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //判断是否需要登录
        String path = exchange.getRequest().getPath().toString();
        if (isLoginRequiredForPath(path)) {
            ServerHttpResponse response = exchange.getResponse();
            response.setRawStatusCode(401);
            return response.setComplete();
        }
        //不需要登录，放行
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }


    /**
     * 判断传入的地址是否需要登录
     *
     * @param path
     * @return
     */
    private boolean isLoginRequiredForPath(String path) {
        for (String pattern : authProperties.getExcludePaths()) {
            if (pathMatcher.match(pattern, path)) {
                return false;
            }
        }
        return true;
    }
}
