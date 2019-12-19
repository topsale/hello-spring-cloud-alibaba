package com.funtl.spring.cloud.alibaba.business;

import com.funtl.spring.cloud.alibaba.business.fallback.ProviderAdminFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "provider-admin", fallback = ProviderAdminFallback.class)
public interface ProviderAdminFeign {

    @GetMapping(value = "/echo/{string}")
    String echo(@PathVariable("string") String string);

    @GetMapping(value = "/lb")
    String lb();
}
