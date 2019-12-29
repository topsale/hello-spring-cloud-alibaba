package com.funtl.spring.cloud.alibaba.provider.service.impl;

import com.funtl.spring.cloud.alibaba.provider.domain.TOrderItem;
import com.funtl.spring.cloud.alibaba.provider.mapper.TOrderItemMapper;
import com.funtl.spring.cloud.alibaba.provider.service.TOrderItemService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service(version = "1.0.0")
@Transactional(readOnly = true)
public class TOrderItemServiceImpl implements TOrderItemService {

    @Resource
    private TOrderItemMapper tOrderItemMapper;

    @Override
    @Transactional(readOnly = false)
    public int insert(TOrderItem orderItem) {
        return tOrderItemMapper.insert(orderItem);
    }
}
