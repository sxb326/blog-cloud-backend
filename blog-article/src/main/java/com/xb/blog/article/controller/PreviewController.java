package com.xb.blog.article.controller;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.xb.blog.common.core.constants.Result;
import com.xb.blog.common.core.pojo.ArticleDocument;
import com.xb.blog.common.core.utils.UserUtil;
import com.xb.blog.article.common.utils.IpUtil;
import com.xb.blog.article.feign.SearchFeignService;
import com.xb.blog.article.service.ArticleService;
import com.xb.blog.article.service.CommentService;
import com.xb.blog.article.vo.ArticleListVo;
import com.xb.blog.article.vo.ArticlePreviewVo;
import com.xb.blog.article.vo.CommentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/preview")
public class PreviewController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private SearchFeignService searchFeignService;

    @GetMapping("/{id}")
    public Result getArticleById(HttpServletRequest request, @PathVariable("id") String id) {
        ArticlePreviewVo vo = articleService.getArticlePreviewById(id);
        if (vo != null) {
            //统计点击量
            String ip = IpUtil.getClientIp(request);
            String key = "ARTICLE_CLICK_IP_SET_" + id;
            Long l = redisTemplate.opsForSet().add(key, ip);
            Date date = new Date();
            DateTime dateTime = DateUtil.endOfMonth(date);
            redisTemplate.expire(key, (dateTime.getTime() - date.getTime()) / 1000, TimeUnit.SECONDS);
            if (l > 0L) {
                //代表这是一个新ip 返回的vo中点击数+1
                vo.setClickCount(vo.getClickCount() + 1);
                //更新文章的点击数
                articleService.updateClickCount(id, 1L);
                //更新es
                ArticleDocument doc = articleService.getArticleDocumentByArticleId(id);
                searchFeignService.publish(doc);
            }
            vo.setIsAuthor(vo.getAuthorId().equals(UserUtil.getUserId()));
            return Result.success(vo);
        }
        return Result.redirect("文章不存在");
    }

    @GetMapping("/comment/{id}/{page}")
    public Result getById(@PathVariable("id") String id, @PathVariable("page") Long page) {
        CommentVo vo = commentService.getTreeDataById(id, null, page);
        return Result.success(vo);
    }

    /**
     * 列出用户所有文章
     *
     * @param page
     * @param userId
     * @param orderType
     * @return
     */
    @GetMapping("/listByUser")
    public Result listByUser(@RequestParam("page") Long page, @RequestParam("userId") String userId, @RequestParam("orderType") String orderType) {
        List<ArticleListVo> list = articleService.listByUser(page, userId, orderType);
        return Result.success(list);
    }
}
