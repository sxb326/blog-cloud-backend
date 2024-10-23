package com.xb.blog.gateway.config.security.service;

import com.xb.blog.common.core.constants.Result;
import com.xb.blog.common.core.dto.AuthUserDto;
import com.xb.blog.gateway.feign.UserService;
import com.xb.blog.gateway.pojo.AuthUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * 自定义用户数据源
 */
@Slf4j
@Component
public class AuthenticationReactiveUserDetailsService implements ReactiveUserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        Result<AuthUserDto> result = userService.findByUsername(username);
        if ("0".equals(result.getCode())) {
            Object data = result.getData();
            if (data != null) {
                AuthUserDto dto = (AuthUserDto) data;
                AuthUser authUser = new AuthUser();
                authUser.setUid(dto.getUid());
                authUser.setUsername(dto.getUsername());
                authUser.setPassword(dto.getPassword());
                return Mono.just(authUser);
            }
        }
        throw new UsernameNotFoundException("用户不存在");
    }
}
