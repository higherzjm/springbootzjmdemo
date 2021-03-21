package com.zjm.control;

import com.zjm.client.ProviderClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 * @version 1.0
 **/
@RestController
public class ConsumerController {

    //动态代理对象，内部远程调用服务生产者
    @Autowired
    ProviderClient providerClient;


    @GetMapping("/feignServiceTest/{name}")
    public String feignServiceTest(@PathVariable("name") String name){
        String  feignServiceTest = providerClient.studentName(name);

        return "feign跨服务调用测试:" +feignServiceTest;

    }
}
