package com.xb.blog.gateway.constants;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "auth")
@Component
public class AuthProperties {
    private List<String> excludePaths;
}
