package com.xb.blog.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xb.blog.web.dao.BlogDao;
import com.xb.blog.web.entity.BlogEntity;
import com.xb.blog.web.service.BlogService;
import org.springframework.stereotype.Service;


@Service
public class BlogServiceImpl extends ServiceImpl<BlogDao, BlogEntity> implements BlogService {

}