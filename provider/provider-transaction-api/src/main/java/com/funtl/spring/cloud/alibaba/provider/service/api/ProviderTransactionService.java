package com.funtl.spring.cloud.alibaba.provider.service.api;

import com.funtl.spring.cloud.alibaba.provider.domain.TbOrder;
import com.funtl.spring.cloud.alibaba.provider.domain.TbOrderItem;

public interface ProviderTransactionService {
    /**
     * 创建订单
     * @param order
     * @param orderItem 实际这里应该放集合，为了演示方便就偷懒了
     */
    void createOrder(TbOrder order, TbOrderItem orderItem);
}
