package com.xb.blog.gateway.feign;

import com.xb.blog.common.core.constants.Result;
import com.xb.blog.common.core.dto.AuthUserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "blog-user")
public interface UserService {

    @GetMapping("/user/findByUsername/{username}")
    Result<AuthUserDto> findByUsername(@PathVariable("username") String username);

    @PostMapping("/user/updatePassword")
    Result updatePassword(@RequestParam("username") String username, @RequestParam("newPassword") String newPassword);
}
