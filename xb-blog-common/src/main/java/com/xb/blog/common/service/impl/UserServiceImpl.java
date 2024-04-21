package com.xb.blog.common.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.xb.blog.common.dao.UserDao;
import com.xb.blog.common.entity.UserEntity;
import com.xb.blog.common.service.UserService;


@Service
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {

}