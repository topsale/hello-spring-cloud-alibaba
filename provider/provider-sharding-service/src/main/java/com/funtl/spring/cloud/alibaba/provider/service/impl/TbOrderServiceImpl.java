package com.funtl.spring.cloud.alibaba.provider.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.funtl.spring.cloud.alibaba.provider.mapper.TbOrderMapper;
import com.funtl.spring.cloud.alibaba.provider.service.api.TbOrderService;
@Service
public class TbOrderServiceImpl implements TbOrderService{

    @Resource
    private TbOrderMapper tbOrderMapper;

}
