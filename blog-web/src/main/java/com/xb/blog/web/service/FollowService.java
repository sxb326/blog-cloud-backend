package com.xb.blog.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xb.blog.web.entity.FollowEntity;
import com.xb.blog.web.vo.FollowSaveVo;

public interface FollowService extends IService<FollowEntity> {
    void save(FollowSaveVo vo);
}
