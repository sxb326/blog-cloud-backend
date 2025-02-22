package com.xb.blog.article.feign;

import com.xb.blog.common.core.constants.Result;
import com.xb.blog.common.core.pojo.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("blog-user")
public interface UserFeignService {

    @GetMapping("/user/getUserInfo")
    Result<UserInfo> getUserInfo(@RequestParam("id") String id);
}
