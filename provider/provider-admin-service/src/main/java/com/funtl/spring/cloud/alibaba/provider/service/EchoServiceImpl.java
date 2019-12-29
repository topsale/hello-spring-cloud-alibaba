package com.funtl.spring.cloud.alibaba.provider.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.funtl.spring.cloud.alibaba.provider.fallback.EchoFallback;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Value;

@Service(version = "1.0.0")
public class EchoServiceImpl implements EchoService {

    @Value("${server.port}")
    private String port;

    /**
     * 熔断器的使用
     *
     * <p>
     * 1.  {@link SentinelResource#value()} 对应的是 Sentinel 控制台中的资源，可用作控制台设置【流控】和【降级】操作 <br>
     * 2.  {@link SentinelResource#fallback()} 对应的是 {@link EchoFallback#echoFallback(String, Throwable)}，并且必须为 `static` <br>
     * 3. 如果不设置 {@link SentinelResource#fallbackClass()}，则需要在当前类中创建一个 `Fallback` 函数，函数签名与原函数一致或加一个 {@link Throwable} 类型的参数
     * </p>
     *
     * @param string {@code String}
     * @return {@code String}
     */
    @Override
    @SentinelResource(value = "echo", fallback = "echoFallback", fallbackClass = EchoFallback.class)
    public String echo(String string) {
        // 增加一段异常代码，用于测试熔断
        if ("test".equals(string)) {
            throw new IllegalArgumentException("invalid arg");
        }
        return "Echo Hello Dubbo " + string + " i am from port: " + port;
    }
}
