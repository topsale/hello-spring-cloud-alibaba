package com.funtl.spring.cloud.alibaba.provider.service.impl;

import com.funtl.spring.cloud.alibaba.commons.cache.LettuceUtils;
import com.funtl.spring.cloud.alibaba.provider.service.api.ProviderCacheService;
import org.apache.dubbo.config.annotation.Service;

import javax.annotation.Resource;

@Service(version = "1.0.0")
public class ProviderCacheServiceImpl implements ProviderCacheService {

    @Resource
    private LettuceUtils lettuceUtils;

    @Override
    public void set(String key, String value) {
        lettuceUtils.set(key, value);
    }

    @Override
    public String get(String key) {
        return lettuceUtils.get(key);
    }

    @Override
    public void delete(String key) {
        lettuceUtils.delete(key);
    }
}
