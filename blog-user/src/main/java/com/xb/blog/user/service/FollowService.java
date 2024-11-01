package com.xb.blog.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xb.blog.user.entity.FollowEntity;
import com.xb.blog.user.vo.FollowSaveVo;

public interface FollowService extends IService<FollowEntity> {
    void save(FollowSaveVo vo);
}
