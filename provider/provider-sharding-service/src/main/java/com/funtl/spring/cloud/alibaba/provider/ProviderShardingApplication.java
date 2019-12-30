package com.funtl.spring.cloud.alibaba.provider;

import com.funtl.spring.cloud.alibaba.configuration.DubboSentinelConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackageClasses = {ProviderShardingApplication.class, DubboSentinelConfiguration.class})
@MapperScan(basePackages = "com.funtl.spring.cloud.alibaba.provider.mapper")
public class ProviderShardingApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProviderShardingApplication.class, args);
    }
}
