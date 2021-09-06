package com.zjm.controller;

import com.zjm.service.MyService_Bean;
import com.zjm.service.MyService_Import;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/myController")
@Import({MyService_Import.class})
@Configuration //注入import service一定要加 @Configuration
public class MyController {
    @Autowired
    private MyService_Bean myService_bean;
    @Autowired
    private MyService_Import myService_import;
    @GetMapping("/myMethod")
    public String myMethod() {
        return "test";
    }
    @GetMapping("/myMethod2")
    public void myMethod2() {
        System.out.println("myMethod2");
    }
    public MyController() {
        System.out.println("myController 初始化");
    }
    @Bean
    public MyService_Bean myService_Bean(){
        return new MyService_Bean();
    }

}
