package com.funtl.spring.cloud.alibaba.provider;

import com.funtl.spring.cloud.alibaba.configuration.DubboSentinelConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackageClasses = {ProviderTransactionApplication.class, DubboSentinelConfiguration.class})
public class ProviderTransactionApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProviderTransactionApplication.class, args);
    }
}
