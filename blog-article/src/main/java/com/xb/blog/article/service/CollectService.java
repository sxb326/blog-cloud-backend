package com.xb.blog.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xb.blog.article.entity.CollectEntity;
import com.xb.blog.article.vo.CollectSaveVo;

public interface CollectService extends IService<CollectEntity> {
    /**
     * 收藏文章
     *
     * @param vo
     */
    void save(CollectSaveVo vo);
}
