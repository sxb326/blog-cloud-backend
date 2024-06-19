package com.xb.blog.auth.config.security;

import com.xb.blog.auth.config.security.filter.AuthFilter;
import com.xb.blog.auth.config.security.handler.AuthAuthenticationFailureHandler;
import com.xb.blog.auth.config.security.handler.AuthAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthAuthenticationSuccessHandler authAuthenticationSuccessHandler;

    @Autowired
    private AuthAuthenticationFailureHandler authAuthenticationFailureHandler;

    /**
     * 配置过滤器链
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().permitAll();
        http.csrf().disable();
    }

    /**
     * 提供自定义登录过滤器，定义从post请求体中获取登录请求参数
     *
     * @return
     */
    @Bean
    public AuthFilter authFilter() throws Exception {
        AuthFilter filter = new AuthFilter();
        //认证管理器
        filter.setAuthenticationManager(authenticationManagerBean());
        //认证成功处理器
        filter.setAuthenticationSuccessHandler(authAuthenticationSuccessHandler);
        //认证失败处理器
        filter.setAuthenticationFailureHandler(authAuthenticationFailureHandler);
        return filter;
    }

    /**
     * 提供认证管理器
     *
     * @return
     * @throws Exception
     */
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
