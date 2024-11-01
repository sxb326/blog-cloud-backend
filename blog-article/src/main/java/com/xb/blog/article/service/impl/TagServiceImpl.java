package com.xb.blog.article.service.impl;

import com.xb.blog.article.dao.TagDao;
import com.xb.blog.article.service.TagService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.xb.blog.article.entity.TagEntity;


@Service
public class TagServiceImpl extends ServiceImpl<TagDao, TagEntity> implements TagService {

}