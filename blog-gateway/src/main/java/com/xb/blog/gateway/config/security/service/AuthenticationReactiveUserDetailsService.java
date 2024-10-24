package com.xb.blog.gateway.config.security.service;

import com.xb.blog.common.core.constants.Result;
import com.xb.blog.common.core.dto.AuthUserDto;
import com.xb.blog.gateway.feign.UserService;
import com.xb.blog.gateway.pojo.AuthUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.ReactiveUserDetailsPasswordService;
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
public class AuthenticationReactiveUserDetailsService implements ReactiveUserDetailsService, ReactiveUserDetailsPasswordService {

    @Autowired
    private UserService userService;

    /**
     * 根据用户名查询用户
     *
     * @param username the username to look up
     * @return
     */
    @Override
    public Mono<UserDetails> findByUsername(String username) {
        Result<AuthUserDto> result = userService.findByUsername(username);
        if ("0".equals(result.getCode())) {
            Object data = result.getData();
            if (data != null) {
                AuthUserDto dto = (AuthUserDto) data;
                AuthUser authUser = new AuthUser();
                authUser.setId(dto.getId());
                authUser.setUsername(dto.getUsername());
                authUser.setPassword(dto.getPassword());
                return Mono.just(authUser);
            }
        }
        throw new UsernameNotFoundException("用户不存在");
    }

    /**
     * 密码升级
     *
     * @param user        the user to modify the password for
     * @param newPassword the password to change to
     * @return
     */
    @Override
    public Mono<UserDetails> updatePassword(UserDetails user, String newPassword) {
        Result result = userService.updatePassword(user.getUsername(), newPassword);
        if ("0".equals(result.getCode())) {
            AuthUser authUser = (AuthUser) user;
            authUser.setPassword(newPassword);
            return Mono.just(authUser);
        }
        return Mono.just(user);
    }
}
