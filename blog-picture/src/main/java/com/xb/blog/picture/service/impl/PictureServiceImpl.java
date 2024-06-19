package com.xb.blog.picture.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xb.blog.picture.dao.PictureDao;
import com.xb.blog.picture.entity.PictureEntity;
import com.xb.blog.picture.service.PictureService;
import org.springframework.stereotype.Service;

@Service
public class PictureServiceImpl extends ServiceImpl<PictureDao, PictureEntity> implements PictureService {
}
