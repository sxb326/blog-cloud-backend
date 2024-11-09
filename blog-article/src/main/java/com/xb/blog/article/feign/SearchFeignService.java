package com.xb.blog.article.feign;

import com.xb.blog.common.core.constants.Result;
import com.xb.blog.common.core.dto.BehaviorLogDto;
import com.xb.blog.common.core.pojo.ArticleDocument;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("blog-search")
public interface SearchFeignService {

    @Async
    @PostMapping("/publish")
    Result publish(@RequestBody ArticleDocument doc);

    @Async
    @PostMapping("/behaviorLog/save")
    Result saveBehaviorLog(@RequestBody BehaviorLogDto dto);
}
