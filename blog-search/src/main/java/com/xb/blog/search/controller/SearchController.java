package com.xb.blog.search.controller;

import cn.hutool.json.JSONUtil;
import com.xb.blog.common.constants.Result;
import com.xb.blog.common.pojo.BlogDocument;
import com.xb.blog.common.vo.SearchVo;
import com.xb.blog.search.service.SearchService;
import com.xb.blog.search.strategy.SearchServiceStrategy;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class SearchController {


    @Autowired
    private SearchServiceStrategy searchStrategy;

    /**
     * 根据关键字 从es检索文章数据
     *
     * @param keyword
     * @return
     */
    @GetMapping("/search")
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

    /**
     * 根据传入的doc对象，上传文章数据到es
     *
     * @param doc
     * @return
     */
    @PostMapping("/publish")
    public Result publish(@RequestBody BlogDocument doc) {
        IndexRequest indexRequest = new IndexRequest("blog_list");

        indexRequest.id(doc.getUid());
        indexRequest.source(JSONUtil.toJsonStr(doc), XContentType.JSON);

        try {
//            IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
            return Result.success("文章上传到es成功！");
        } catch (Exception e) {
            return Result.error("文章上传到es失败！" + e.getMessage());
        }
    }
}
