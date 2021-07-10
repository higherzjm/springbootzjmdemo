package com.zjm.kindsmodels.Strategy.example;

import org.springframework.stereotype.Component;

@Component("strategyB")
public class ResourceB implements Strategy {
 
    @Override
    public String getVpcList(String id) {
        System.out.println("B strategy"+"====="+id);
        return "²ßÂÔ:"+id;
    }
 
}