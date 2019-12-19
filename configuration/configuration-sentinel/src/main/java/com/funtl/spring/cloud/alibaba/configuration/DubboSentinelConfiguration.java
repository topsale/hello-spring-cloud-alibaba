package com.funtl.spring.cloud.alibaba.configuration;

import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Dubbo Sentinel AOP
 * <p>
 * Description: 仅 Dubbo 服务需要该配置，Spring Cloud Alibaba 无需加载该配置
 * </p>
 *
 * @author Lusifer
 * @version v1.0.0
 * @date 2019-12-20 05:51:23
 * @see com.funtl.spring.cloud.alibaba.configuration
 */
@Configuration
public class DubboSentinelConfiguration {

    @Bean
    public SentinelResourceAspect sentinelResourceAspect() {
        return new SentinelResourceAspect();
    }

}
