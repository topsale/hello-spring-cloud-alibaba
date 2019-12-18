package com.funtl.spring.cloud.alibaba.business.controller;

import com.funtl.spring.cloud.alibaba.business.ProviderAdminFeign;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RefreshScope
@RestController
public class TestEchoController {

    @Resource
    private ProviderAdminFeign providerAdminFeign;

    @Value("${user.name}")
    private String username;

    @GetMapping(value = "/feign/echo/{str}")
    public String echo(@PathVariable String str) {
        return providerAdminFeign.echo(str);
    }

    @GetMapping(value = "/config")
    public String config() {
        return providerAdminFeign.echo(username);
    }

    @GetMapping(value = "/lb")
    public String lb() {
        return providerAdminFeign.lb();
    }
}
