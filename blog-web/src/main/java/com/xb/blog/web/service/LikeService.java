package com.xb.blog.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xb.blog.web.entity.LikeEntity;
import com.xb.blog.web.vo.LikeSaveVo;

public interface LikeService extends IService<LikeEntity> {
    /**
     * 保存点赞行为
     *
     * @param vo
     */
    Long save(LikeSaveVo vo);
}
