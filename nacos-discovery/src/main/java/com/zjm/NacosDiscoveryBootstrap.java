package com.zjm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 * @version 1.0
 **/
@SpringBootApplication
@RestController
public class NacosDiscoveryBootstrap {
    public static void main(String[] args) {
        SpringApplication.run(NacosDiscoveryBootstrap.class, args);
    }


    //http://localhost:8083/test
    @GetMapping("/test")
    public String test() {
        return "test";
    }

}
