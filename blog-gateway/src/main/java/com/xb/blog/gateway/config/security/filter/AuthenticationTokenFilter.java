package com.xb.blog.gateway.config.security.filter;

import cn.hutool.core.util.StrUtil;
import com.xb.blog.common.core.constants.Result;
import com.xb.blog.common.core.dto.AuthUserDto;
import com.xb.blog.common.core.utils.AuthUtil;
import com.xb.blog.gateway.feign.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * token过滤器：解析请求头中的token 并放到上下文中 方便后面对用户登录状态进行判断
 */
@Component
public class AuthenticationTokenFilter implements WebFilter {

    @Autowired
    private UserService userService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String token = exchange.getRequest().getHeaders().getFirst("Token");

        if (StrUtil.isNotBlank(token)) {
            String username = AuthUtil.getLoginUsername(token);
            Result<AuthUserDto> result = userService.findByUsername(username);
            if ("0".equals(result.getCode())) {
                AuthUserDto dto = result.getData();
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword(), AuthorityUtils.NO_AUTHORITIES);
                return chain.filter(exchange).subscriberContext(ReactiveSecurityContextHolder.withAuthentication(authentication));
            }
        }
        return chain.filter(exchange);
    }
}
