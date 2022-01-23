package com.zjm.springframework.cyclicdependence;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
//@AllArgsConstructor
@Data
public class ServiceB {
    //private final ServiceA serviceA2;
    @Autowired
    private ServiceA serviceA;
/*
    public ServiceB() {
        System.out.println("初始化完成ServiceB");
    }*/
}
