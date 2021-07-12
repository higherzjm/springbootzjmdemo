package com.zjm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MyBaseSpringBootApplication {

    /**
     * druid: http://localhost:8081/druid/login.html  admin/123456
     * swagger： http://localhost:8081/swagger-ui.html
     **/
    public static void main(String[] args) {
        SpringApplication.run(MyBaseSpringBootApplication.class, args);
    }

}