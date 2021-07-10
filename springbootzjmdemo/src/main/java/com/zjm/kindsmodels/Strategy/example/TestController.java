package com.zjm.kindsmodels.Strategy.example;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/java23kindModels")
@Api(tags = "java23种设计模式")
public class TestController {
 
    @Autowired
    private SimpleContext simpleContext;
 
    @GetMapping("/strategyModel")
    @ApiOperation(value = "策略设计模式", notes = "策略设计模式-多种策略选择其一")
    public String choose(@RequestParam String poolId){
        return simpleContext.getResource(poolId);
    }
 
}