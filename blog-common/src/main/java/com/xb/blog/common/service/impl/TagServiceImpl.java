package com.xb.blog.common.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.xb.blog.common.dao.TagDao;
import com.xb.blog.common.entity.TagEntity;
import com.xb.blog.common.service.TagService;


@Service
public class TagServiceImpl extends ServiceImpl<TagDao, TagEntity> implements TagService {

}