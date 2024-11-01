package com.xb.blog.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xb.blog.common.core.utils.UserUtil;
import com.xb.blog.user.dao.UserDao;
import com.xb.blog.user.entity.UserEntity;
import com.xb.blog.user.service.UserService;
import com.xb.blog.user.vo.UserInfoVo;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {
    @Override
    public UserEntity findByUsername(String username) {
        UserEntity user = getOne(new QueryWrapper<UserEntity>().eq("username", username));
        return user;
    }

    @Override
    public UserInfoVo getUserInfo(String userId) {
        return baseMapper.getUserInfo(userId, UserUtil.getUserId());
    }

    @Override
    public void updatePassword(String username, String newPassword) {
        baseMapper.updatePassword(username, newPassword);
    }
}