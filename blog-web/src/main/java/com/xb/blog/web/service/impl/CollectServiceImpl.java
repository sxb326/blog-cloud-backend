package com.xb.blog.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xb.blog.web.dao.CollectDao;
import com.xb.blog.web.entity.CollectEntity;
import com.xb.blog.web.service.CollectService;
import org.springframework.stereotype.Service;

@Service
public class CollectServiceImpl extends ServiceImpl<CollectDao, CollectEntity> implements CollectService {
}
