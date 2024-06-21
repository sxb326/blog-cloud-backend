package com.xb.blog.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xb.blog.web.dao.BlogTagDao;
import com.xb.blog.web.entity.BlogTagEntity;
import com.xb.blog.web.service.BlogTagService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BlogTagServiceImpl extends ServiceImpl<BlogTagDao, BlogTagEntity> implements BlogTagService {
    /**
     * 根据传入的博客id 和 标签id集合，保存博客和标签的关联数据
     *
     * @param blogUid
     * @param tagUids
     */
    @Override
    public void save(String blogUid, List<String> tagUids) {
        remove(new QueryWrapper<BlogTagEntity>().eq("blog_uid", blogUid));
        if (tagUids != null) {
            List<BlogTagEntity> entities = tagUids.stream()
                    .map(tagUid -> {
                        BlogTagEntity blogTag = new BlogTagEntity();
                        blogTag.setBlogUid(blogUid);
                        blogTag.setTagUid(tagUid);
                        blogTag.setStatus(1);
                        return blogTag;
                    }).collect(Collectors.toList());
            saveBatch(entities);
        }
    }
}
