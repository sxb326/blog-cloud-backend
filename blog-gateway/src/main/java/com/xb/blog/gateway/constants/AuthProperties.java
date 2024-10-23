package com.xb.blog.gateway.constants;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "auth")
@Component
public class AuthProperties {
    private String[] excludePaths;
}
