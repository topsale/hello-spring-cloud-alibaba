package com.funtl.spring.cloud.alibaba.provider.service.impl;

import com.funtl.spring.cloud.alibaba.provider.domain.TOrder;
import com.funtl.spring.cloud.alibaba.provider.domain.TOrderItem;
import com.funtl.spring.cloud.alibaba.provider.mapper.TOrderMapper;
import com.funtl.spring.cloud.alibaba.provider.service.TOrderItemService;
import com.funtl.spring.cloud.alibaba.provider.service.TOrderService;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.apache.shardingsphere.transaction.annotation.ShardingTransactionType;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service(version = "1.0.0")
@Transactional(readOnly = true)
public class TOrderServiceImpl implements TOrderService {

    @Resource
    private TOrderMapper tOrderMapper;

    @Reference(version = "1.0.0")
    private TOrderItemService orderItemService;

    @Override
    @Transactional(readOnly = false)
    @ShardingTransactionType(TransactionType.BASE)
    public void insert(TOrder order, TOrderItem orderItem) {
        tOrderMapper.insert(order);
        orderItemService.insert(orderItem);
        throw new RuntimeException("模拟异常，测试分布式事务回滚");
    }
}
