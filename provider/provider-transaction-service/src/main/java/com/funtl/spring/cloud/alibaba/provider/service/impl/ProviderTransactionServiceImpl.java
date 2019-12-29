package com.funtl.spring.cloud.alibaba.provider.service.impl;

import com.funtl.spring.cloud.alibaba.provider.domain.TbOrder;
import com.funtl.spring.cloud.alibaba.provider.domain.TbOrderItem;
import com.funtl.spring.cloud.alibaba.provider.service.api.ProviderTransactionService;
import com.funtl.spring.cloud.alibaba.provider.service.api.TbOrderItemService;
import com.funtl.spring.cloud.alibaba.provider.service.api.TbOrderService;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;

@Service(version = "1.0.0")
public class ProviderTransactionServiceImpl implements ProviderTransactionService {

    @Reference(version = "1.0.0")
    private TbOrderService orderService;

    @Reference(version = "1.0.0")
    private TbOrderItemService orderItemService;

    @Override
    @GlobalTransactional
    public void createOrder(TbOrder order, TbOrderItem orderItem) {
        System.out.println(" 开始全局事务，XID = " + RootContext.getXID());
        orderService.insert(order);
        orderItemService.insert(orderItem);
        throw new RuntimeException("Exception for seata.");
    }
}
