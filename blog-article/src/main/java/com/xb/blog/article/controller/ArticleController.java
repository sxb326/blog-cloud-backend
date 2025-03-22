package com.xb.blog.article.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xb.blog.article.entity.ArticleEntity;
import com.xb.blog.article.entity.ArticleTagEntity;
import com.xb.blog.article.entity.DraftEntity;
import com.xb.blog.article.service.ArticleService;
import com.xb.blog.article.service.ArticleTagService;
import com.xb.blog.article.service.DraftService;
import com.xb.blog.article.vo.ArticleEditorVo;
import com.xb.blog.common.core.constants.Result;
import com.xb.blog.common.core.constants.ResultEnum;
import com.xb.blog.common.core.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private DraftService draftService;

    @Autowired
    private ArticleTagService articleTagService;

    @GetMapping("/id")
    public Result id() {
        return Result.success(null, UUID.randomUUID().toString().replace("-", ""));
    }

    @GetMapping("/{id}")
    public Result getArticleById(@PathVariable("id") String id) {
        String userId = UserUtil.getUserId();

        //先判断是否存在草稿
        DraftEntity draft = draftService.getById(id);
        if (draft != null) {
            if (!userId.equals(draft.getAuthor())) {
                return Result.build(ResultEnum.NO_PERMISSION);
            }
            ArticleEditorVo vo = new ArticleEditorVo();
            BeanUtil.copyProperties(draft, vo);
            vo.setTagIds(articleTagService.list(new QueryWrapper<ArticleTagEntity>().eq("article_id", draft.getId()))
                    .stream().map(ArticleTagEntity::getTagId).collect(Collectors.toList()));
            return Result.success(vo);
        }

        //获取博客
        ArticleEntity article = articleService.getById(id);
        if (article != null) {
            if (!userId.equals(article.getAuthor())) {
                return Result.build(ResultEnum.NO_PERMISSION);
            }
            ArticleEditorVo vo = new ArticleEditorVo();
            BeanUtil.copyProperties(article, vo);
            vo.setTagIds(articleTagService.list(new QueryWrapper<ArticleTagEntity>().eq("article_id", article.getId()))
                    .stream().map(ArticleTagEntity::getTagId).collect(Collectors.toList()));
            return Result.success(vo);
        }

        return Result.success(null);
    }

    /**
     * 发布文章
     * 1、没有草稿 直接保存到博客表
     * 2、有草稿 删除草稿 保存博客 修改博客状态
     * 3、将博客推送到es 可以被搜索到
     *
     * @param vo
     * @return
     */
    @PostMapping("/publish")
    public Result publish(@RequestBody ArticleEditorVo vo) {
        articleService.publish(vo);
        return Result.success("发布成功！");
    }

    /**
     * 根据文章id获取点赞数
     *
     * @param id
     * @return
     */
    @GetMapping("/likeCount/{id}")
    public Result likeCount(@PathVariable("id") String id) {
        Long count = articleService.getLikeCount(id);
        return Result.success(count);
    }

    @GetMapping("/collectCount/{id}")
    public Result collectCount(@PathVariable("id") String id) {
        Long count = articleService.getCollectCount(id);
        return Result.success(count);
    }
}
