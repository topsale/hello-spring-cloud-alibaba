package com.funtl.spring.cloud.alibaba.provider.fallback;

import lombok.extern.slf4j.Slf4j;

/**
 * Dubbo 服务提供者熔断器
 * <p>
 * Description:
 * </p>
 *
 * @author Lusifer
 * @version v1.0.0
 * @date 2019-12-20 06:15:46
 * @see com.funtl.spring.cloud.alibaba.provider.fallback
 */
@Slf4j
public class EchoFallback {

    /**
     * 熔断方法
     *
     * @param string {@code String}
     * @param ex     {@code Throwable} 异常信息
     * @return {@code String} 熔断后的固定结果
     */
    public static String echoFallback(String string, Throwable ex) {
        log.warn("Invoke echo: " + ex.getClass().getTypeName());
        ex.printStackTrace();
        return "echo fallback";
    }

}
