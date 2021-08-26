package com.zjm.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;

@Service
@Import({MyCustomerService.class})
public class MyService {
    public MyService() {
        System.out.println("Service 初始化");
    }

    @Bean
    public MyCustomObject initMyCustomObject(){
        return new MyCustomObject();
    }
}
