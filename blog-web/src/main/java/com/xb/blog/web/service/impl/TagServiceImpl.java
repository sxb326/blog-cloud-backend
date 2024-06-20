package com.xb.blog.web.service.impl;

import com.xb.blog.web.dao.TagDao;
import com.xb.blog.web.service.TagService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.xb.blog.web.entity.TagEntity;


@Service
public class TagServiceImpl extends ServiceImpl<TagDao, TagEntity> implements TagService {

}