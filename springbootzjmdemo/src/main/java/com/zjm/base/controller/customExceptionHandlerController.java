package com.zjm.base.controller;

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
@Api(tags = "�Զ����쳣������")
public class customExceptionHandlerController {
    @GetMapping("/customExceptionHandlerTest1/{dividendNum}/{divisorNum}")
    @ApiOperation(value = "�Զ����쳣����������1", notes = "�Զ����쳣����������1")
    public float customExceptionHandlerTest1(@ApiParam(name = "dividendNum", value = "������") @PathVariable("dividendNum") int dividendNum,
                                              @ApiParam(name = "divisorNum", value = "����") @PathVariable("divisorNum") int divisorNum) {
        float ret=dividendNum/divisorNum;
        return  ret;
    }
}
