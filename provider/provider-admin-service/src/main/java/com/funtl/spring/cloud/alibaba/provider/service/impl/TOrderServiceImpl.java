package com.funtl.spring.cloud.alibaba.provider.service.impl;

import com.funtl.spring.cloud.alibaba.provider.mapper.TOrderMapper;
import com.funtl.spring.cloud.alibaba.provider.service.TOrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TOrderServiceImpl implements TOrderService {

    @Resource
    private TOrderMapper tOrderMapper;

}







