package com.xb.blog.search.feign;

import com.xb.blog.common.core.constants.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("blog-article")
public interface BlogFeignService {
    @GetMapping("/article/blog/search")
    Result search(@RequestParam("keyword") String keyword, @RequestParam("page") Long page);
}
