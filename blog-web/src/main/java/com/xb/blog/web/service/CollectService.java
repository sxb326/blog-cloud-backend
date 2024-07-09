package com.xb.blog.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xb.blog.web.entity.CollectEntity;
import com.xb.blog.web.vo.CollectSaveVo;

public interface CollectService extends IService<CollectEntity> {
    /**
     * 收藏文章
     *
     * @param vo
     */
    void save(CollectSaveVo vo);
}
