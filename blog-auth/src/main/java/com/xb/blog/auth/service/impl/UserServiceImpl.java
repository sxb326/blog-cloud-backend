package com.xb.blog.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xb.blog.auth.dao.UserDao;
import com.xb.blog.auth.entitiy.UserEntity;
import com.xb.blog.auth.service.UserService;
import com.xb.blog.auth.vo.UserInfoVo;
import com.xb.blog.common.core.utils.UserUtil;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {

    /**
     * 根据用户ID 获取用户信息
     *
     * @param userUid
     * @return
     */
    @Override
    public UserInfoVo getUserInfo(String userUid) {
        return baseMapper.getUserInfo(userUid, UserUtil.getUserId());
    }
}