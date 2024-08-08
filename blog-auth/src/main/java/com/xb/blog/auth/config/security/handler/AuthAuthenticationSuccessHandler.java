package com.xb.blog.auth.config.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xb.blog.auth.config.security.model.AuthUser;
import com.xb.blog.common.core.constants.Result;
import com.xb.blog.common.core.utils.AuthUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义认证成功处理器
 */
@Component
public class AuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        //登录成功，生成token，并保存到响应头中
        AuthUser user = (AuthUser) authentication.getPrincipal();
        String token = AuthUtil.createToken(user.getUid(), user.getUsername());
        response.setHeader("Token", token);
        response.setHeader("Access-Control-Expose-Headers", "Token");

        response.setContentType("application/json;charset=utf-8");
        Result result = new Result();
        result.setCode("0");
        result.setMessage("登录成功");
        response.getWriter().write(new ObjectMapper().writeValueAsString(result));
    }
}
