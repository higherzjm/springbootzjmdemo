package com.zjm.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by Administrator.
 */
@FeignClient(value = "nacos-discovery",url ="${higher.providerFeign.url}" )
public interface ProviderClient {

    @GetMapping("/studentName/{name}")
    public String studentName(@PathVariable("name") String name);
}
