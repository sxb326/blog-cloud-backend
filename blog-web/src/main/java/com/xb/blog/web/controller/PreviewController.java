package com.xb.blog.web.controller;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.xb.blog.common.constants.Result;
import com.xb.blog.web.common.utils.IpUtil;
import com.xb.blog.web.service.BlogService;
import com.xb.blog.web.service.CommentService;
import com.xb.blog.web.vo.BlogPreviewVo;
import com.xb.blog.web.vo.CommentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/preview")
public class PreviewController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @GetMapping("/{id}")
    public Result getBlogById(HttpServletRequest request, @PathVariable("id") String id) {
        BlogPreviewVo vo = blogService.getBlogPreviewById(id);
        if (vo != null) {
            //统计点击量
            String ip = IpUtil.getClientIp(request);
            String key = "BLOG_CLICK_IP_SET_" + id;
            Long l = redisTemplate.opsForSet().add(key, ip);
            Date date = new Date();
            DateTime dateTime = DateUtil.endOfMonth(date);
            redisTemplate.expire(key, (dateTime.getTime() - date.getTime()) / 1000, TimeUnit.SECONDS);
            if (l > 0L) {
                //代表这是一个新ip 返回的vo中点击数+1
                vo.setClickCount(vo.getClickCount() + 1);
                //更新文章的点击数
                blogService.updateClickCount(id, 1L);
            }
            return Result.success(vo);
        }
        return Result.redirect("文章不存在");
    }

    @GetMapping("/comment/{id}/{page}")
    public Result getById(@PathVariable("id") String id, @PathVariable("page") Long page) {
        CommentVo vo = commentService.getTreeDataById(id, null, page);
        return Result.success(vo);
    }
}
