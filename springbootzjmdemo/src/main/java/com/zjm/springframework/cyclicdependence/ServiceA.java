package com.zjm.springframework.cyclicdependence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * 循环依赖测试,属性注入spring已经用三级缓存解决了循环依赖问题，构造器注入没有
 */
@Service
//@AllArgsConstructor //构造器注入注解
//@NoArgsConstructor
@Data
//@Scope("prototype")
public class ServiceA {
    //private String name;
    @Autowired
    private ServiceB serviceB;
  /*  @Lazy
   private final ServiceB serviceB;*/
    public ServiceA() {
        System.out.println("初始化完成ServiceA");
    }

}
