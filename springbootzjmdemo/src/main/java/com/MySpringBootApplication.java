package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class MySpringBootApplication {

    /**
     * druid: http://localhost:8081/druid/login.html  admin/123456
     * swagger： http://localhost:8081/swagger-ui.html
     **/
    public static void main(String[] args) {
        SpringApplication.run(MySpringBootApplication.class, args);
    }

}