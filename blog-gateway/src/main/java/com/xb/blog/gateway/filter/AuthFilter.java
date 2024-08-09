package com.xb.blog.gateway.filter;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xb.blog.common.core.constants.Result;
import com.xb.blog.common.core.utils.AuthUtil;
import com.xb.blog.gateway.constants.AuthProperties;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;

/**
 * 认证过滤器：
 * 将这个过滤器配置在 NettyRoutingFilter 之前，实现在路由转发之前进行登录校验工作
 */
@Component
public class AuthFilter implements GlobalFilter, Ordered {

    @Autowired
    private AuthProperties authProperties;

    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @SneakyThrows(IOException.class)
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //判断如果是websocket请求，不需要判断 直接放行
        if ("websocket".equals(exchange.getRequest().getHeaders().getUpgrade())) {
            return chain.filter(exchange);
        }
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        //判断是否需要登录
        if (isLoginRequiredForPath(request.getPath().toString())) {
            //判断用户是否已经登录
            if (!isAuth(request)) {
                Result result = new Result();
                result.setCode("999");
                result.setMessage("请先登录");
                byte[] bytes = new ObjectMapper().writeValueAsBytes(result);
                return response.writeWith(Mono.fromSupplier(() -> response.bufferFactory().wrap(bytes)));
            }
        }
        //不需要登录&已登录，放行
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

    /**
     * 判断该请求是否已经登录
     *
     * @param request
     * @return
     */
    private boolean isAuth(ServerHttpRequest request) {
        //获取请求头中的token
        List<String> headers = request.getHeaders().get("Token");
        String token = "";
        if (!CollUtil.isEmpty(headers)) {
            token = headers.get(0);
        }
        if (StrUtil.isBlank(token)) {
            return false;
        }

        //校验token
        return AuthUtil.isAuth(token);
    }
}
