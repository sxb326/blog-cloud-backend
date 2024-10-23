package com.xb.blog.gateway.config.openfeign;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.RandomUtil;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.reactive.ReactiveLoadBalancer;
import org.springframework.cloud.loadbalancer.blocking.client.BlockingLoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * OpenFeign配置类：
 * 由于Spring Cloud Gateway使用非阻塞式的WebFlux框架，而OpenFeign默认是基于阻塞调用
 * 因此需要一些额外配置来适配非阻塞环境，确保OpenFeign能够正常工作。
 */
@Configuration
public class OpenFeignConfig {

    @Bean
    public BlockingLoadBalancerClient blockingLoadBalancerClient(ReactiveLoadBalancer.Factory<ServiceInstance> loadBalancerClientFactory,
                                                                 DiscoveryClient discoveryClient) {
        return new BlockingLoadBalancerClient(loadBalancerClientFactory) {
            @Override
            public <T> ServiceInstance choose(String serviceId, Request<T> request) {
                List<ServiceInstance> instanceList = discoveryClient.getInstances(serviceId);
                if (CollUtil.isEmpty(instanceList)) {
                    return null;
                }
                if (instanceList.size() == 1) {
                    return instanceList.get(0);
                }
                int index = RandomUtil.randomInt(0, instanceList.size());
                return instanceList.get(index);
            }
        };
    }

    @Bean
    public HttpMessageConverters httpMessageConverters() {
        return new HttpMessageConverters();
    }
}
