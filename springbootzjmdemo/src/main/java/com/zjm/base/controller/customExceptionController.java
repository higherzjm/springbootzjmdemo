package com.zjm.base.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author zhujianming
 */
@RequestMapping("/base/customExceptionController")
@RestController
@Slf4j
@Api(tags = "自定义异常处理器")
public class customExceptionController {
    @GetMapping("/test")
    @ApiOperation(value = "自定义异常处理器测试1", notes = "自定义异常处理器测试1")
    public String generateSecurtykey() {
        int m=10/0;
        return  "测试成功";
    }
}
