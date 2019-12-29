package com.funtl.spring.cloud.alibaba.provider.service.impl;

import com.funtl.spring.cloud.alibaba.provider.domain.TbOrder;
import com.funtl.spring.cloud.alibaba.provider.mapper.TbOrderMapper;
import com.funtl.spring.cloud.alibaba.provider.service.api.TbOrderService;
import io.seata.core.context.RootContext;
import org.apache.dubbo.config.annotation.Service;

import javax.annotation.Resource;

@Service(version = "1.0.0")
public class TbOrderServiceImpl implements TbOrderService {

    @Resource
    private TbOrderMapper tbOrderMapper;

    @Override
    public void insert(TbOrder order) {
        System.out.println(" 全局事务 id ：" + RootContext.getXID());
        tbOrderMapper.insert(order);
    }
}

