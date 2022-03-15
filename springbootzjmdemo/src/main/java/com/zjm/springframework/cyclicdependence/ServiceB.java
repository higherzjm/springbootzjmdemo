package com.zjm.springframework.cyclicdependence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;


@Service
//@AllArgsConstructor
//@NoArgsConstructor
@Data
//@Scope("prototype")
public class ServiceB {
   // private Integer age;
  /*  @Lazy
    private final ServiceA serviceA;*/
    @Autowired
    private ServiceA serviceA;
    public ServiceB() {
        System.out.println("初始化完成ServiceB");
    }
}
