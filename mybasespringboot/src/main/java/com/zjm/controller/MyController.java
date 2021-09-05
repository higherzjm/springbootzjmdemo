package com.zjm.controller;

import com.zjm.service.MyCustomObject;
import com.zjm.service.MyCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/myController")
@Import({MyCustomerService.class})
@Configuration //注入import service一定要加 @Configuration
public class MyController {
    @Autowired
    private MyCustomObject myCustomObject;
    @Autowired
    private MyCustomerService myCustomerService;
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

}
