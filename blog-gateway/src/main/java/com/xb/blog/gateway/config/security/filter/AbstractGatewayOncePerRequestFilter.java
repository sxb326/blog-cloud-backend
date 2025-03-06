package com.xb.blog.gateway.config.security.filter;

import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * 参考 webmvc 中的 org.springframework.web.filter.OncePerRequestFilter
 * 在 ServerWebExchange 中设置已执行标记，防止过滤器被执行多次
 */
public abstract class AbstractGatewayOncePerRequestFilter implements WebFilter {

    public static final String ALREADY_FILTERED_SUFFIX = ".FILTERED";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        //拼接过滤器执行过的标记名称
        String alreadyFilteredAttributeName = getClass().getName() + ALREADY_FILTERED_SUFFIX;

        //判断当前过滤器是否已执行过
        if (exchange.getAttribute(alreadyFilteredAttributeName) == null) {

            //当前过滤器未执行过 设置标记并执行
            exchange.getAttributes().put(alreadyFilteredAttributeName, true);

            return doFilter(exchange, chain);
        }

        //当前过滤器已执行过 跳过
        return chain.filter(exchange);
    }

    abstract Mono<Void> doFilter(ServerWebExchange exchange, WebFilterChain chain);
}
