package com.xb.blog.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xb.blog.web.dao.FavoriteDao;
import com.xb.blog.web.entity.FavoriteEntity;
import com.xb.blog.web.service.FavoriteService;
import org.springframework.stereotype.Service;

@Service
public class FavoriteServiceImpl extends ServiceImpl<FavoriteDao, FavoriteEntity> implements FavoriteService {
}
