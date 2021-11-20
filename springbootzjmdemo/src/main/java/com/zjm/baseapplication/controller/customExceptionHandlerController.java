package com.zjm.baseapplication.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author zhujianming
 */
@RequestMapping("/base/customExceptionHandlerController")
@RestController
@Slf4j
@Api(tags = "自定义异常处理器")
public class customExceptionHandlerController {
    @GetMapping("/customExceptionHandlerTest1/{dividendNum}/{divisorNum}")
    @ApiOperation(value = "自定义异常处理器测试1", notes = "自定义异常处理器测试1")
    public float customExceptionHandlerTest1(@ApiParam(name = "dividendNum", value = "被除数") @PathVariable("dividendNum") int dividendNum,
                                              @ApiParam(name = "divisorNum", value = "除数") @PathVariable("divisorNum") int divisorNum) {
        float ret=dividendNum/divisorNum;
        return  ret;
    }
}
