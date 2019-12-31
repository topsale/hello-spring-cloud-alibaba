package com.funtl.spring.cloud.alibaba.provider.tests;

import com.funtl.spring.cloud.alibaba.provider.service.api.ProviderCacheService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProviderCacheServiceTests {

    @Resource
    private ProviderCacheService providerCacheService;

    @Test
    public void testSet() {
        providerCacheService.set("name", "李晓红");
    }

    @Test
    public void testGet() {
        providerCacheService.get("name");
    }

    @Test
    public void testDelete() {
        providerCacheService.delete("name");
    }
}
