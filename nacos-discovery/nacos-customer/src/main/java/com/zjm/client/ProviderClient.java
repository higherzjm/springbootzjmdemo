package com.zjm.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by Administrator.
 */
@FeignClient(value = "nacos-provide",url ="${higher.providerFeign.url}" )
public interface ProviderClient {

    @GetMapping("/studentName/{name}")
     String studentName(@PathVariable("name") String name);
}
