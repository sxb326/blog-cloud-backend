package com.xb.blog.search.controller;

import cn.hutool.json.JSONUtil;
import com.xb.blog.common.core.constants.Result;
import com.xb.blog.common.core.dto.BehaviorLogDto;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/behaviorLog")
public class BehaviorLogController {

    @Autowired
    private RestHighLevelClient client;

    @PostMapping("/save")
    public Result save(@RequestBody BehaviorLogDto dto) {
        IndexRequest indexRequest = new IndexRequest("behavior_log");

        indexRequest.source(JSONUtil.toJsonStr(dto), XContentType.JSON);

        try {
            client.index(indexRequest, RequestOptions.DEFAULT);
            return Result.success("保存成功！");
        } catch (Exception e) {
            return Result.error("保存失败！" + e.getMessage());
        }
    }
}
