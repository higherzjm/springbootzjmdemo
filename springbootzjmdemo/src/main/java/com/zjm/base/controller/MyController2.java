package com.zjm.base.controller;

import org.springframework.web.bind.annotation.*;

/**
 * @author zhujianming
 */
@RequestMapping("/myboot2")
@RestController
public class MyController2 {

    @RequestMapping("/test2")
    public String test2() {
        return  "test";
    }

}

