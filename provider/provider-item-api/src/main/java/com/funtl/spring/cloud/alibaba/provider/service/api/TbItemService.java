package com.funtl.spring.cloud.alibaba.provider.service.api;

import com.funtl.spring.cloud.alibaba.provider.domain.TbItem;

public interface TbItemService {

    /**
     * 使用 RLock 对象操作分布式锁
     *
     * @param item {@link TbItem}
     * @return {@code int} num >= 0 则表示操作成功
     */
    int updateByRLock(TbItem item);

    /**
     * 使用 AOP 操作分布式锁
     * @param item {@link TbItem}
     * @return {@code int} num >= 0 则表示操作成功
     */
    int updateByAop(TbItem item);
}
