package com.funtl.spring.cloud.alibaba.provider.service.api;

public interface ProviderCacheService {
    void set(String key, String value);
    String get(String key);
    void delete(String key);
}
