package com.funtl.spring.cloud.alibaba.provider.service;

import com.funtl.spring.cloud.alibaba.provider.domain.TOrder;
import com.funtl.spring.cloud.alibaba.provider.domain.TOrderItem;

public interface TOrderService {

    void insert(TOrder order, TOrderItem orderItem);

}
