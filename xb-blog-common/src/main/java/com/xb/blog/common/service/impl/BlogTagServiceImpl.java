package com.xb.blog.common.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.xb.blog.common.dao.BlogTagDao;
import com.xb.blog.common.entity.BlogTagEntity;
import com.xb.blog.common.service.BlogTagService;


@Service
public class BlogTagServiceImpl extends ServiceImpl<BlogTagDao, BlogTagEntity> implements BlogTagService {

}