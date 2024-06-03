package com.xb.blog.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xb.blog.common.dao.BlogDao;
import com.xb.blog.common.entity.BlogEntity;
import com.xb.blog.common.service.BlogService;
import org.springframework.stereotype.Service;


@Service
public class BlogServiceImpl extends ServiceImpl<BlogDao, BlogEntity> implements BlogService {

}