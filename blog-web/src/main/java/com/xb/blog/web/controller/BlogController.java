package com.xb.blog.web.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xb.blog.common.constants.Result;
import com.xb.blog.web.common.utils.UserUtil;
import com.xb.blog.web.entity.BlogEntity;
import com.xb.blog.web.entity.BlogTagEntity;
import com.xb.blog.web.entity.DraftEntity;
import com.xb.blog.web.service.BlogService;
import com.xb.blog.web.service.BlogTagService;
import com.xb.blog.web.service.DraftService;
import com.xb.blog.web.vo.BlogEditorVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private DraftService draftService;

    @Autowired
    private BlogTagService blogTagService;

    @GetMapping("/id")
    public Result id() {
        return Result.success(null, UUID.randomUUID().toString().replace("-", ""));
    }

    @GetMapping("/{id}")
    public Result getBlogById(@PathVariable("id") String id) {
        String userId = UserUtil.getUserId();

        //先判断是否存在草稿
        DraftEntity draft = draftService.getById(id);
        if (draft != null) {
            if (!userId.equals(draft.getAuthor())) {
                return Result.redirect("权限不足");
            }
            BlogEditorVo vo = new BlogEditorVo();
            BeanUtil.copyProperties(draft, vo);
            vo.setTagUids(blogTagService.list(new QueryWrapper<BlogTagEntity>().eq("blog_uid", draft.getUid()))
                    .stream().map(BlogTagEntity::getTagUid).collect(Collectors.toList()));
            return Result.success(vo);
        }

        //获取博客
        BlogEntity blog = blogService.getById(id);
        if (blog != null) {
            if (!userId.equals(blog.getAuthor())) {
                return Result.redirect("权限不足");
            }
            BlogEditorVo vo = new BlogEditorVo();
            BeanUtil.copyProperties(blog, vo);
            vo.setTagUids(blogTagService.list(new QueryWrapper<BlogTagEntity>().eq("blog_uid", blog.getUid()))
                    .stream().map(BlogTagEntity::getTagUid).collect(Collectors.toList()));
            return Result.success(vo);
        }

        return Result.success(null);
    }

    /**
     * 发布文章
     * 1、没有草稿 直接保存到博客表
     * 2、有草稿 同步到博客表 删除草稿 修改博客状态
     * 3、将博客推送到es 可以被搜索到
     *
     * @param vo
     * @return
     */
    @PostMapping("/publish")
    public Result publish(@RequestBody BlogEditorVo vo) {
        blogService.publish(vo);
        return Result.success("发布成功！");
    }

    /**
     * 根据博客id获取点赞数
     *
     * @param id
     * @return
     */
    @GetMapping("/likeCount/{id}")
    public Result likeCount(@PathVariable("id") String id) {
        Long count = blogService.getLikeCount(id);
        return Result.success(count);
    }

    @GetMapping("/collectCount/{id}")
    public Result collectCount(@PathVariable("id") String id) {
        Long count = blogService.getCollectCount(id);
        return Result.success(count);
    }
}
