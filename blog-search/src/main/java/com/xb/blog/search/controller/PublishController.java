package com.xb.blog.search.controller;

import cn.hutool.json.JSONUtil;
import com.xb.blog.common.core.annotation.InternalApi;
import com.xb.blog.common.core.constants.Result;
import com.xb.blog.common.core.pojo.ArticleDocument;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
@RequestMapping("/publish")
public class PublishController {

    @Autowired
    private RestHighLevelClient client;

    /**
     * 根据传入的doc对象，上传文章数据到es
     *
     * @param doc
     * @return
     */
    @InternalApi
    @PostMapping
    public Result publish(@RequestBody ArticleDocument doc) {
        IndexRequest indexRequest = new IndexRequest("article_list");

        indexRequest.id(doc.getId());
        indexRequest.source(JSONUtil.toJsonStr(doc), XContentType.JSON);

        try {
            IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
            log.error("\n\n上传成功\n\n");
            return Result.success("文章上传到es成功！");
        } catch (Exception e) {
            return Result.error("文章上传到es失败！" + e.getMessage());
        }
    }
}
