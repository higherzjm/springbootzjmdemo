package com.zjm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 * @version 1.0
 **/
@SpringBootApplication
@RestController
@EnableFeignClients
@EnableDiscoveryClient
public class NacosCustomerBootstrap {
    public static void main(String[] args) {
        SpringApplication.run(NacosCustomerBootstrap.class, args);
    }


    //http://localhost:8084/test
    @GetMapping("/test")
    public String test() {
        return "NacosCustomer";
    }


}
