package com.xb.blog.web.service.impl;

import com.xb.blog.web.dao.BlogTagDao;
import com.xb.blog.web.service.BlogTagService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.xb.blog.web.entity.BlogTagEntity;


@Service
public class BlogTagServiceImpl extends ServiceImpl<BlogTagDao, BlogTagEntity> implements BlogTagService {

}