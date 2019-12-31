package com.funtl.spring.cloud.alibaba.provider;

import com.funtl.spring.cloud.alibaba.configuration.DubboSentinelConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackageClasses = {ProviderCacheApplication.class, DubboSentinelConfiguration.class})
@ComponentScan(basePackages = "com.funtl.spring.cloud.alibaba.commons")
public class ProviderCacheApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProviderCacheApplication.class, args);
    }
}
