package com.funtl.spring.cloud.alibaba.provider.service.impl;

import com.funtl.spring.cloud.alibaba.provider.mapper.TbOrderItemMapper;
import com.funtl.spring.cloud.alibaba.provider.service.api.TbOrderItemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TbOrderItemServiceImpl implements TbOrderItemService {

    @Resource
    private TbOrderItemMapper tbOrderItemMapper;

}
