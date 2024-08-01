package com.xb.blog.search.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.xb.blog.common.constants.Result;
import com.xb.blog.common.pojo.BlogDocument;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/blog")
public class BlogSearchController {

    @Autowired
    private RestHighLevelClient client;

    /**
     * 根据关键字 从es检索文章数据
     *
     * @param keyword
     * @return
     */
    @GetMapping("/search")
    public Result search(String keyword) {
        SearchRequest searchRequest = new SearchRequest("blog_list");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        if (StrUtil.isNotBlank(keyword)) {
            MatchQueryBuilder query = QueryBuilders.matchQuery("title", keyword);
            searchSourceBuilder.query(query);
        }

        searchRequest.source(searchSourceBuilder);

        try {
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits hits = searchResponse.getHits();
            List<BlogDocument> dtos = new ArrayList<>();
            if (hits.getHits() != null) {
                dtos = Arrays.stream(hits.getHits())
                        .map(hit -> JSONUtil.toBean(hit.getSourceAsString(), BlogDocument.class)).collect(Collectors.toList());
            }
            return Result.success("检索成功！", dtos);
        } catch (Exception e) {
            return Result.error("检索失败！");
        }
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
            IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
            return Result.success("文章上传到es成功！");
        } catch (Exception e) {
            return Result.error("文章上传到es失败！" + e.getMessage());
        }
    }
}
