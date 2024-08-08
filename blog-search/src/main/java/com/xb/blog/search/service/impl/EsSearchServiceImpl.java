package com.xb.blog.search.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.xb.blog.common.core.pojo.BlogDocument;
import com.xb.blog.common.core.vo.SearchVo;
import com.xb.blog.search.service.SearchService;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 从Es检索
 */
@Service("es")
public class EsSearchServiceImpl implements SearchService {

    @Autowired
    private RestHighLevelClient client;

    /**
     * 根据传入的关键字以及分页参数，检索数据 封装为SearchVo并返回
     *
     * @param keyword
     * @param page
     * @return
     */
    @Override
    public SearchVo search(String keyword, Long page) {
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
            return vo;
        } catch (Exception e) {
            return null;
        }
    }
}
