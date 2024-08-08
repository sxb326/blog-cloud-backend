package com.xb.blog.search.controller;

import com.xb.blog.common.core.constants.Result;
import com.xb.blog.common.core.vo.SearchVo;
import com.xb.blog.search.service.SearchService;
import com.xb.blog.search.strategy.SearchServiceStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
public class SearchController {


    @Autowired
    private SearchServiceStrategy searchStrategy;

    /**
     * 根据关键字 从es检索文章数据
     *
     * @param keyword
     * @return
     */
    @GetMapping
    public Result search(String keyword, Long page) {
        //查询系统配置的检索方式 mysql、es todo 先写死 模拟系统参数
        String type = "es";
        SearchService searchService = searchStrategy.getService(type);
        if (searchService == null) {
            return Result.error("检索失败！系统配置错误！");
        }
        SearchVo vo = searchService.search(keyword, page);
        searchService.beforeReturn(vo);
        return Result.success("检索成功！", vo);
    }
}
