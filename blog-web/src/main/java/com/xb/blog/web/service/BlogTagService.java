package com.xb.blog.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xb.blog.web.entity.BlogTagEntity;

import java.util.List;

public interface BlogTagService extends IService<BlogTagEntity> {
    /**
     * 根据传入的博客id 和 标签id集合，保存博客和标签的关联数据
     *
     * @param blogId
     * @param tagIds
     */
    void save(String blogId, List<String> tagIds);
}
