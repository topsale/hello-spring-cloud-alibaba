package com.funtl.spring.cloud.alibaba.provider.tests;

import com.funtl.spring.cloud.alibaba.provider.domain.TbOrder;
import com.funtl.spring.cloud.alibaba.provider.mapper.TbOrderMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MapperTests {

    @Autowired
    private TbOrderMapper orderMapper;

    @Test
    public void testInsert1() {
        // 按照配置文件中的规则
        TbOrder order = new TbOrder();
        // userId 为奇数进奇数库即 myshop_1
        order.setUserId(1L);
        // orderId 为奇数进奇数表即 tb_order_1
        order.setOrderId(1L);
        // 此时数据应插入 myshop_1.tb_order_1 中
        orderMapper.insert(order);
    }

    @Test
    public void testInsert2() {
        // 按照配置文件中的规则
        TbOrder order = new TbOrder();
        // userId 为偶数进偶数库即 myshop_0
        order.setUserId(2L);
        // orderId 为偶数进偶数表即 tb_order_0
        order.setOrderId(2L);
        // 此时数据应插入 myshop_0.tb_order_0 中
        orderMapper.insert(order);
    }

    @Test
    public void testSelectAll() {
        List<TbOrder> tOrders = orderMapper.selectAll();
        tOrders.forEach(tOrder -> {
            System.out.println(tOrder);
        });
    }
}
