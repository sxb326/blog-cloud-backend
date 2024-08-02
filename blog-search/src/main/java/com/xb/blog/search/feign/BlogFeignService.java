package com.xb.blog.search.feign;

import com.xb.blog.common.constants.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("blog-web")
public interface BlogFeignService {
    @GetMapping("/web/blog/search")
    Result search(@RequestParam("keyword") String keyword, @RequestParam("page") Long page);
}
