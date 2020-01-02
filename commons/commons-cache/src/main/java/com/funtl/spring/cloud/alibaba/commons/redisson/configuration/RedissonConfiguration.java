package com.funtl.spring.cloud.alibaba.commons.redisson.configuration;

import com.funtl.spring.cloud.alibaba.commons.redisson.RedissonBinary;
import com.funtl.spring.cloud.alibaba.commons.redisson.RedissonCollection;
import com.funtl.spring.cloud.alibaba.commons.redisson.RedissonObject;
import com.funtl.spring.cloud.alibaba.commons.redisson.aop.RedissonLockAop;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfiguration {
    @Bean
    @ConditionalOnMissingBean(RedissonObject.class)
    public RedissonObject redissonObject() {
        return new RedissonObject();
    }

    @Bean
    @ConditionalOnMissingBean(RedissonBinary.class)
    public RedissonBinary redissonBinary() {
        return new RedissonBinary();
    }

    @Bean
    @ConditionalOnMissingBean(RedissonCollection.class)
    public RedissonCollection redissonCollection() {
        return new RedissonCollection();
    }

    @Bean
    @ConditionalOnMissingBean(RedissonLockAop.class)
    public RedissonLockAop redissonLockAop() {
        return new RedissonLockAop();
    }
}
