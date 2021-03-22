package com.zjm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 * @version 1.0
 **/
@SpringBootApplication
@RestController
@EnableDiscoveryClient
public class NacosProvideBootstrap {
    public static void main(String[] args) {
        SpringApplication.run(NacosProvideBootstrap.class, args);
    }


    //http://localhost:8083/test
    @GetMapping("/test")
    public String test() {
        return "NacosProvideBootstrap";
    }

    @GetMapping("/studentName/{name}")
    public String studentName(@PathVariable("name") String name){
        System.out.println("我是服务提供者，接收的参数为："+name);
        return "my name is "+name;
    }

}
