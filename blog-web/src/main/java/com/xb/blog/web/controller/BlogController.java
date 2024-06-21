package com.xb.blog.web.controller;

import cn.hutool.core.bean.BeanUtil;
import com.xb.blog.common.constants.Result;
import com.xb.blog.common.utils.AuthUtil;
import com.xb.blog.web.common.context.UserContext;
import com.xb.blog.web.entity.BlogEntity;
import com.xb.blog.web.entity.DraftEntity;
import com.xb.blog.web.service.BlogService;
import com.xb.blog.web.service.DraftService;
import com.xb.blog.web.vo.BlogEditorVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private DraftService draftService;

    @GetMapping("/id")
    public Result id() {
        return Result.success(null, UUID.randomUUID().toString().replace("-", ""));
    }

    @GetMapping("/{id}")
    public Result getBlogById(@PathVariable("id") String id) {
        String userId = UserContext.getUserId();

        //先判断是否存在草稿
        DraftEntity draft = draftService.getById(id);
        if (draft != null) {
            if (!userId.equals(draft.getAuthor())) {
                return Result.unauthorized();
            }
            BlogEditorVo vo = new BlogEditorVo();
            BeanUtil.copyProperties(draft, vo);
            return Result.success(vo);
        }

        //获取博客
        BlogEntity blog = blogService.getById(id);
        if (blog != null) {
            if (!userId.equals(blog.getAuthor())) {
                return Result.unauthorized();
            }
            BlogEditorVo vo = new BlogEditorVo();
            BeanUtil.copyProperties(blog, vo);
            return Result.success(vo);
        }

        return Result.success(null);
    }

    /**
     * 保存草稿
     *
     * @param vo
     * @return
     */
    @PostMapping("/draft")
    public Result saveDraft(@RequestBody BlogEditorVo vo) {
        String userId = UserContext.getUserId();
        vo.setAuthor(userId);
        String draftUid = draftService.saveDraft(vo);
        return Result.success("保存草稿成功！", draftUid);
    }

    /**
     * 发布文章
     * 1、没有草稿 直接保存到博客表
     * 2、有草稿 同步到博客表 删除草稿 修改博客装填
     * 3、将博客推送到es 可以被搜索到
     *
     * @param vo
     * @return
     */
    @PostMapping("/publish")
    public Result publish(@RequestBody BlogEditorVo vo) {
        return null;
    }
}
