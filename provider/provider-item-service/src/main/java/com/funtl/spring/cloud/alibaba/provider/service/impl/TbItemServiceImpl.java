package com.funtl.spring.cloud.alibaba.provider.service.impl;

import com.funtl.spring.cloud.alibaba.commons.redisson.annotation.RedissonLock;
import com.funtl.spring.cloud.alibaba.commons.redisson.enums.RedissonLockModel;
import com.funtl.spring.cloud.alibaba.provider.domain.TbItem;
import com.funtl.spring.cloud.alibaba.provider.mapper.TbItemMapper;
import com.funtl.spring.cloud.alibaba.provider.service.api.TbItemService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import javax.annotation.Resource;

@Slf4j
@Service(version = "1.0.0")
public class TbItemServiceImpl implements TbItemService {

    @Resource
    private TbItemMapper tbItemMapper;

    @Resource
    private RedissonClient redissonClient;

    @Override
    public int updateByRLock(TbItem item) {
        int newNum = -1;

        // 加锁，此处根据商品名称加锁
        RLock lock = redissonClient.getLock(item.getTitle());
        lock.lock();
        log.info("Thread {} 拿到了 {} 的锁", Thread.currentThread().getId(), item.getTitle());

        try {
            // 阻塞模拟业务操作时间
            Thread.sleep(10000);
            int currentNum = tbItemMapper.selectByPrimaryKey(item).getNum();
            newNum = currentNum - item.getNum();
            if (newNum >= 0) {
                item.setNum(newNum);
                tbItemMapper.updateByPrimaryKey(item);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 解锁
        lock.unlock();
        log.info("Thread {} 释放了 {} 的锁", Thread.currentThread().getId(), item.getTitle());

        return newNum;
    }

    @Override
    @RedissonLock(keys = "#item.title", lockModel = RedissonLockModel.AUTO)
    public int updateByAop(TbItem item) {
        int newNum = -1;

        try {
            Thread.sleep(2000);
            int currentNum = tbItemMapper.selectByPrimaryKey(item).getNum();
            newNum = currentNum - item.getNum();
            if (newNum >= 0) {
                item.setNum(newNum);
                tbItemMapper.updateByPrimaryKey(item);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return newNum;
    }
}
