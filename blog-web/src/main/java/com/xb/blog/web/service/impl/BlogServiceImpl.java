package com.xb.blog.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xb.blog.web.common.utils.UserUtil;
import com.xb.blog.web.dao.BlogDao;
import com.xb.blog.web.entity.BlogEntity;
import com.xb.blog.web.service.BlogService;
import com.xb.blog.web.service.BlogTagService;
import com.xb.blog.web.service.DraftService;
import com.xb.blog.web.vo.BlogEditorVo;
import com.xb.blog.web.vo.BlogListVo;
import com.xb.blog.web.vo.BlogPreviewVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class BlogServiceImpl extends ServiceImpl<BlogDao, BlogEntity> implements BlogService {

    @Autowired
    private DraftService draftService;

    @Autowired
    private BlogTagService blogTagService;

    /**
     * 发布博客
     *
     * @param vo
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void publish(BlogEditorVo vo) {
        //保存博客表
        BlogEntity blog = new BlogEntity();
        BeanUtil.copyProperties(vo, blog);
        blog.setStatus(1);
        blog.setAuthor(UserUtil.getUserId());
        saveOrUpdate(blog);

        //保存标签绑定数据
        blogTagService.save(vo.getUid(), vo.getTagUids());

        //删除草稿表
        draftService.removeById(vo.getUid());

    }

    /**
     * 查询博客列表
     *
     * @return
     */
    @Override
    public List<BlogListVo> listBlog() {
        return baseMapper.listBlog();
    }

    /**
     * 根据id获取博客预览数据
     *
     * @param id
     * @return
     */
    @Override
    public BlogPreviewVo getBlogPreviewById(String id) {
        return baseMapper.getBlogPreviewById(id, UserUtil.getUserId());
    }
}