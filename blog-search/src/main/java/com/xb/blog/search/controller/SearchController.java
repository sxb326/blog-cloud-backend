package com.xb.blog.search.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.xb.blog.common.constants.Result;
import com.xb.blog.common.pojo.BlogDocument;
import com.xb.blog.search.vo.SearchVo;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping
public class SearchController {

    @Autowired
    private RestHighLevelClient client;

    /**
     * 根据关键字 从es检索文章数据
     *
     * @param keyword
     * @return
     */
    @GetMapping("/search")
    public Result search(String keyword, Long page) {
        if (page == null) page = 1L;
        page = (page - 1L) * 10L;

        SearchRequest searchRequest = new SearchRequest("blog_list");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        //设置检索参数
        if (StrUtil.isNotBlank(keyword)) {
            searchSourceBuilder.query(QueryBuilders.matchQuery("title", keyword));
            //设置检索关键字高亮
            HighlightBuilder builder = new HighlightBuilder();
            builder.field("title");
            builder.preTags("<span style='color:red'>");
            builder.postTags("</span>");
            searchSourceBuilder.highlighter(builder);
        }

        //设置分页参数
        searchSourceBuilder.from(page.intValue());
        searchSourceBuilder.size(10);

        searchRequest.source(searchSourceBuilder);

        try {
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits hits = searchResponse.getHits();
            SearchVo vo = new SearchVo();
            if (hits.getHits() != null) {
                vo.setTotal(hits.getTotalHits().value);
                List<BlogDocument> list = Arrays.stream(hits.getHits())
                        .map(hit -> {
                            BlogDocument blogDocument = JSONUtil.toBean(hit.getSourceAsString(), BlogDocument.class);
                            //处理关键字高亮
                            HighlightField title = hit.getHighlightFields().get("title");
                            blogDocument.setTitle(title.getFragments()[0].toString());
                            return blogDocument;
                        }).collect(Collectors.toList());
                vo.setList(list);
            }
            return Result.success("检索成功！", vo);
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
