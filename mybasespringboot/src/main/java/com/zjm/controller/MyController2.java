package com.zjm.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/myController2")
public class MyController2 {
    @GetMapping("/myMethod21")
    public String myMethod() {
        return "test";
    }
    @GetMapping("/myMethod22")
    public void myMethod2() {
        System.out.println("myMethod2");
    }
    public MyController2() {
        System.out.println("myController 初始化");
    }

}
