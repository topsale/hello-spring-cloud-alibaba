package com.funtl.spring.cloud.alibaba.business.controller;

import com.funtl.spring.cloud.alibaba.provider.api.EchoService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "dubbo")
public class TestDubboController {

    @Reference(version = "1.0.0")
    private EchoService echoService;

    @GetMapping(value = "echo/{string}")
    public String echo(@PathVariable String string) {
        return echoService.echo(string);
    }
}
