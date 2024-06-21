package com.xb.blog.web.common.interceptor;

import cn.hutool.core.util.StrUtil;
import com.xb.blog.common.utils.AuthUtil;
import com.xb.blog.web.common.utils.UserUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器：用于将请求头中的用户信息解析 封装userId到UserContext中
 */
@Component
public class UserInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Token");
        if (StrUtil.isNotBlank(token)) {
            String uid = AuthUtil.getLoginUid(token);
            UserUtil.setUserId(uid);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserUtil.clear();
    }
}
