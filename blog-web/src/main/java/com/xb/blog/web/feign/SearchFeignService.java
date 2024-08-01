package com.xb.blog.web.feign;

import com.xb.blog.common.constants.Result;
import com.xb.blog.common.pojo.BlogDocument;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("blog-search")
public interface SearchFeignService {

    @PostMapping("/search/blog/publish")
    Result publish(@RequestBody BlogDocument dto);
}
