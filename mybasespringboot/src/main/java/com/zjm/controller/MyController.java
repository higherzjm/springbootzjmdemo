package com.zjm.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/myController")
public class MyController {
    @GetMapping("/myMethod")
    public String myMethod() {
        return "test";
    }

    public MyController() {
        System.out.println("myController 初始化");
    }

}
