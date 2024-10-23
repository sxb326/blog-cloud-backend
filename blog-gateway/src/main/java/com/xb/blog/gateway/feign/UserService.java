package com.xb.blog.gateway.feign;

import com.xb.blog.common.core.constants.Result;
import com.xb.blog.common.core.dto.AuthUserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "blog-web")
public interface UserService {

    @GetMapping("/web/user/findByUsername/{username}")
    Result<AuthUserDto> findByUsername(@PathVariable("username") String username);
}
