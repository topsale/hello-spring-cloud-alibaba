package com.funtl.spring.cloud.alibaba.provider;

import com.funtl.spring.cloud.alibaba.configuration.DubboSentinelConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackageClasses = {ProviderItemApplication.class, DubboSentinelConfiguration.class})
@ComponentScan(basePackages = "com.funtl.spring.cloud.alibaba.commons")
@MapperScan(basePackages = "com.funtl.spring.cloud.alibaba.provider.mapper")
public class ProviderItemApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProviderItemApplication.class, args);
    }
}
