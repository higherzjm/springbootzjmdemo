package com.zjm.springframework.cyclicdependence;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 循环依赖测试,属性注入spring已经用三级缓存解决了循环依赖问题，构造器注入没有
 */
@Service
//@AllArgsConstructor //构造器注入注解
@Data
public class ServiceA {
    @Autowired
    private ServiceB serviceB;
   // private final ServiceB serviceB2;
   /* public ServiceA() {
        System.out.println("初始化完成ServiceA");
    }*/

}
