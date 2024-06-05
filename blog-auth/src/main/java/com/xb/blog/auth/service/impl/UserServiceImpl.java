package com.xb.blog.auth.service.impl;

import com.xb.blog.auth.dao.UserDao;
import com.xb.blog.auth.entitiy.UserEntity;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.xb.blog.auth.service.UserService;


@Service
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {

}