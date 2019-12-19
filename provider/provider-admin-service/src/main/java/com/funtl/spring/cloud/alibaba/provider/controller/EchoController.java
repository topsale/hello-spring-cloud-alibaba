package com.funtl.spring.cloud.alibaba.provider.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.funtl.spring.cloud.alibaba.provider.fallback.EchoFallback;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EchoController {

    @Value("${server.port}")
    private String port;

    @GetMapping(value = "/lb")
    public String lb() {
        return "Hello Nacos Provider i am from port: " + port;
    }

    @GetMapping(value = "/echo/{string}")
    @SentinelResource(value = "echo", fallback = "echoFallback", fallbackClass = EchoFallback.class)
    public String echo(@PathVariable String string) {
        return "Hello Nacos Provider " + string;
    }
}
