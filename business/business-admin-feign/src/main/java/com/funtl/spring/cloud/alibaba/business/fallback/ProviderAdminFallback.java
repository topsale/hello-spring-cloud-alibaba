package com.funtl.spring.cloud.alibaba.business.fallback;

import com.funtl.spring.cloud.alibaba.business.ProviderAdminFeign;
import org.springframework.stereotype.Component;

@Component
public class ProviderAdminFallback implements ProviderAdminFeign {
    @Override
    public String echo(String string) {
        return "echo fallback";
    }

    @Override
    public String lb() {
        return "lb fallback";
    }
}
