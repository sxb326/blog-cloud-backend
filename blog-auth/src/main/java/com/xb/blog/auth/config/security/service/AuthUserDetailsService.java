package com.xb.blog.auth.config.security.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xb.blog.auth.config.security.model.AuthUser;
import com.xb.blog.auth.entitiy.UserEntity;
import com.xb.blog.auth.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * 自定义用户数据源Service
 */
@Component
public class AuthUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    /**
     * 根据username查询用户，封装到AuthUser中
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userService.getOne(new QueryWrapper<UserEntity>().eq("username", username));
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        AuthUser authUser = new AuthUser();
        BeanUtils.copyProperties(user, authUser);
        return authUser;
    }
}
