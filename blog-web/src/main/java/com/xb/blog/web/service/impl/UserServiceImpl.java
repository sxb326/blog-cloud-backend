package com.xb.blog.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xb.blog.common.core.utils.UserUtil;
import com.xb.blog.web.dao.UserDao;
import com.xb.blog.web.entity.UserEntity;
import com.xb.blog.web.service.UserService;
import com.xb.blog.web.vo.UserInfoVo;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {
    @Override
    public UserEntity findByUsername(String username) {
        UserEntity user = getOne(new QueryWrapper<UserEntity>().eq("username", username));
        return user;
    }

    @Override
    public UserInfoVo getUserInfo(String userUid) {
        return baseMapper.getUserInfo(userUid, UserUtil.getUserId());
    }
}