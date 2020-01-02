package com.funtl.spring.cloud.alibaba.provider.tests;

import com.funtl.spring.cloud.alibaba.provider.domain.TbItem;
import com.funtl.spring.cloud.alibaba.provider.service.api.TbItemService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedissonTests {

    @Resource
    private TbItemService itemService;

    @Test
    public void testRedissonByRLock() throws InterruptedException {
        for (int i = 0; i < 4; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    TbItem item = new TbItem();
                    item.setId(1000000);
                    item.setTitle("Apple");
                    item.setNum(1);
                    int result = itemService.updateByRLock(item);
                    if (result == 0) {
                        System.out.println("库存不足");
                    }
                }
            }).start();
        }

        Thread.sleep(60 * 1000);
    }

    @Test
    public void testRedissonByAop() throws InterruptedException {
        for (int i = 0; i < 4; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    TbItem item = new TbItem();
                    item.setId(1000000);
                    item.setTitle("Apple");
                    item.setNum(1);
                    int result = itemService.updateByAop(item);
                    if (result == 0) {
                        System.out.println("库存不足");
                    }
                }
            }).start();
        }

        Thread.sleep(60 * 1000);
    }
}
