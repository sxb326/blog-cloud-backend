package com.xb.blog.search.controller;

import com.xb.blog.common.core.constants.Result;
import com.xb.blog.common.core.pojo.ArticleDocument;
import com.xb.blog.common.core.vo.SearchVo;
import com.xb.blog.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    /**
     * 根据关键字 从es检索文章数据
     *
     * @param keyword
     * @return
     */
    @GetMapping
    public Result search(String keyword, Long page) {
        SearchVo vo = searchService.search(keyword, page);
        return Result.success("检索成功！", vo);
    }

    @GetMapping("/list")
    public Result list(@RequestParam("page") Long page, @RequestParam("categoryId") String categoryId, @RequestParam("orderType") String orderType) {
        List<ArticleDocument> list = searchService.list(page, categoryId, orderType);
        return Result.success("检索成功！", list);
    }
}
