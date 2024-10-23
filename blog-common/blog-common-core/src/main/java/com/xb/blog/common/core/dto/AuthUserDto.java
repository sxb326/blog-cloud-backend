package com.xb.blog.common.core.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class AuthUserDto implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 唯一id
     */
    private String id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
}
