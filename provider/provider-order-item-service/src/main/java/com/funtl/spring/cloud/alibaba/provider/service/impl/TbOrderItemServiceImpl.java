package com.funtl.spring.cloud.alibaba.provider.service.impl;

import com.funtl.spring.cloud.alibaba.provider.domain.TbOrderItem;
import com.funtl.spring.cloud.alibaba.provider.mapper.TbOrderItemMapper;
import com.funtl.spring.cloud.alibaba.provider.service.api.TbOrderItemService;
import io.seata.core.context.RootContext;
import org.apache.dubbo.config.annotation.Service;

import javax.annotation.Resource;

@Service(version = "1.0.0")
public class TbOrderItemServiceImpl implements TbOrderItemService {

    @Resource
    private TbOrderItemMapper tbOrderItemMapper;

    @Override
    public void insert(TbOrderItem orderItem) {
        System.out.println(" 全局事务 id ：" + RootContext.getXID());
        tbOrderItemMapper.insert(orderItem);
    }
}
