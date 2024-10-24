package com.xb.blog.gateway.config.security;

import com.xb.blog.gateway.config.security.converter.AuthenticationConverter;
import com.xb.blog.gateway.config.security.filter.AuthenticationTokenFilter;
import com.xb.blog.gateway.config.security.handler.AuthenticationEntryPoint;
import com.xb.blog.gateway.config.security.handler.AuthenticationFailureHandler;
import com.xb.blog.gateway.config.security.handler.AuthenticationSuccessHandler;
import com.xb.blog.gateway.config.security.service.AuthenticationReactiveUserDetailsService;
import com.xb.blog.gateway.constants.AuthProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.DelegatingReactiveAuthenticationManager;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * Spring Security 配置类
 */
@EnableWebFluxSecurity
public class SecurityConfig {

    @Autowired
    private AuthProperties authProperties;

    @Autowired
    private AuthenticationConverter authenticationConverter;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private AuthenticationReactiveUserDetailsService authenticationUserDetailsService;

    @Autowired
    private AuthenticationTokenFilter authenticationTokenFilter;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity) {

        //开启表单登录
        httpSecurity.formLogin()
                //配置登录请求地址
                .loginPage("/api/login")
                //配置认证管理器
                .authenticationManager(reactiveAuthenticationManager())
                //配置未认证拦截回调
                .authenticationEntryPoint(authenticationEntryPoint)
                //配置认证成功处理器
                .authenticationSuccessHandler(authenticationSuccessHandler)
                //配置认证失败处理器
                .authenticationFailureHandler(authenticationFailureHandler);

        //配置拦截规则
        httpSecurity.authorizeExchange(exchange -> {
            //匿名访问路径
            exchange.pathMatchers(authProperties.getExcludePaths()).permitAll();
            //其余路径均需认证
            exchange.anyExchange().authenticated();
        });

        //注册token解析过滤器
        httpSecurity.addFilterAt(authenticationTokenFilter, SecurityWebFiltersOrder.HTTP_BASIC);

        //配置跨域
        httpSecurity.cors().configurationSource(configurationSource());

        //禁用csrf
        httpSecurity.csrf().disable();

        //替换ServerAuthenticationConverter 以便能从post请求体body中获取用户名密码
        SecurityWebFilterChain chain = httpSecurity.build();
        chain.getWebFilters()
                .filter(filter -> filter instanceof AuthenticationWebFilter)
                .subscribe(filter -> {
                    AuthenticationWebFilter webFilter = (AuthenticationWebFilter) filter;
                    webFilter.setServerAuthenticationConverter(authenticationConverter);
                });

        return chain;
    }

    /**
     * 认证管理器
     *
     * @return
     */
    @Bean
    public ReactiveAuthenticationManager reactiveAuthenticationManager() {
        UserDetailsRepositoryReactiveAuthenticationManager manager = new UserDetailsRepositoryReactiveAuthenticationManager(authenticationUserDetailsService);
        manager.setUserDetailsPasswordService(authenticationUserDetailsService);
        return new DelegatingReactiveAuthenticationManager(Arrays.asList(manager));
    }

    /**
     * 跨域相关配置
     *
     * @return
     */
    public CorsConfigurationSource configurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.setAllowedHeaders(Arrays.asList("*"));
        corsConfiguration.setAllowedMethods(Arrays.asList("*"));
        corsConfiguration.setAllowedOrigins(Arrays.asList("*"));
        corsConfiguration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }
}
