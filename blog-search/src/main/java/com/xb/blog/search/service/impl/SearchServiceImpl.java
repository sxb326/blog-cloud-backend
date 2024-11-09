package com.xb.blog.search.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.xb.blog.common.core.pojo.ArticleDocument;
import com.xb.blog.common.core.utils.UserUtil;
import com.xb.blog.common.core.vo.SearchVo;
import com.xb.blog.search.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 检索
 */
@Slf4j
@Service
public class SearchServiceImpl implements SearchService {

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

        SearchRequest searchRequest = new SearchRequest("article_list");

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
                List<ArticleDocument> list = Arrays.stream(hits.getHits())
                        .map(hit -> {
                            ArticleDocument blogDocument = JSONUtil.toBean(hit.getSourceAsString(), ArticleDocument.class);
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

    @Override
    public List<ArticleDocument> list(Long page, String categoryId, String orderType) {
        if (page == null) page = 1L;
        page = (page - 1L) * 10L;

        SearchRequest searchRequest = new SearchRequest("article_list");
        SearchSourceBuilder builder = new SearchSourceBuilder();

        //构建复杂查询条件
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        //设置文章分类检索参数
        if (StrUtil.isNotBlank(categoryId)) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("categoryId", categoryId));
        }

        //如果查看推荐文章，则需要根据用户点赞、评论、收藏过的文章的作者id、分类id、标签 来做过滤
        if ("recommend".equals(orderType)) {
            String userId = UserUtil.getUserId();
            if (StrUtil.isNotBlank(userId)) {
                //用户已登录 按照用户的行为日志来分析
                buildBoolQueryBuilder(boolQueryBuilder);
                builder.query(boolQueryBuilder);
            } else {
                //用户未登录 按照点击量推荐
                builder.sort("clickCount", SortOrder.DESC);
            }
        }

        //如果查看最新文章，只需要根据发布时间倒序排列
        if ("newest".equals(orderType)) {
            builder.sort("publishTime", SortOrder.DESC);
        }

        //设置分页参数
        builder.from(page.intValue());
        builder.size(10);

        try {
            searchRequest.source(builder);
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits hits = searchResponse.getHits();
            if (hits.getHits() != null) {
                return Arrays.stream(hits.getHits())
                        .map(hit -> JSONUtil.toBean(hit.getSourceAsString(), ArticleDocument.class))
                        .collect(Collectors.toList());
            }
        } catch (Exception e) {
            log.error("查询es中的文章列表时报错：{}", e.getMessage(), e);
        }
        return new ArrayList<>();
    }

    /**
     * 构建推荐文章查询条件
     *
     * @return
     */
    private void buildBoolQueryBuilder(BoolQueryBuilder boolQueryBuilder) {
        //创建聚合查询，查询出用户最常关注的 作者、文章分类、文章标签
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.matchPhraseQuery("userId", UserUtil.getUserId()));

        //对常看的作者进行聚合 取前100个最常看的作者
        builder.aggregation(getAggregationBuilder("authorAgg", "targetUserId.keyword", 100));

        //对常看的文章分类进行聚合 取前20个最常看的分类
        builder.aggregation(getAggregationBuilder("categoryAgg", "categoryId.keyword", 20));

        //对常看的文章标签进行聚合 取前50最常看的标签
        builder.aggregation(getAggregationBuilder("tagAgg", "tagIds.keyword", 50));

        SearchRequest searchRequest = new SearchRequest("behavior_log");
        searchRequest.source(builder);
        try {
            SearchResponse result = client.search(searchRequest, RequestOptions.DEFAULT);

            //解析作者id
            boolQueryBuilder.should(parseResultToQueryBuilder(result, "authorAgg", "authorId.keyword"));

            //解析分类id
            boolQueryBuilder.should(parseResultToQueryBuilder(result, "categoryAgg", "categoryId.keyword"));

            //解析标签id
            boolQueryBuilder.should(parseResultToQueryBuilder(result, "tagAgg", "tagIdList.keyword"));

        } catch (Exception e) {
            log.error("构建复杂查询条件时报错：{}", e.getMessage(), e);
        }
    }

    /**
     * 根据传入的聚合名称 聚合字段 数据条数 创建 聚合构建器
     *
     * @param aggName
     * @param fieldName
     * @param size
     * @return
     */
    private AggregationBuilder getAggregationBuilder(String aggName, String fieldName, int size) {
        TermsAggregationBuilder aggregationBuilder = AggregationBuilders.terms(aggName);
        return aggregationBuilder.field(fieldName).size(size).order(BucketOrder.count(false));
    }

    /**
     * 解析查询结果 封装为QueryBuilder
     *
     * @param result
     * @param aggName
     * @param fieldName
     * @return
     */
    private QueryBuilder parseResultToQueryBuilder(SearchResponse result, String aggName, String fieldName) {
        List<String> list = ((ParsedStringTerms) result.getAggregations().get(aggName)).getBuckets().stream()
                .map(bucket -> bucket.getKey().toString()).collect(Collectors.toList());
        return list.isEmpty() ? QueryBuilders.matchAllQuery() : QueryBuilders.termsQuery(fieldName, list);
    }
}
