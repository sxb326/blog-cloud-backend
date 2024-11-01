package com.xb.blog.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xb.blog.article.entity.LikeEntity;
import com.xb.blog.article.vo.LikeSaveVo;

public interface LikeService extends IService<LikeEntity> {
    /**
     * 保存点赞行为
     *
     * @param vo
     */
    Long save(LikeSaveVo vo);
}
