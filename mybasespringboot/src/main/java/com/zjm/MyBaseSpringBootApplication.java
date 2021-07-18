package com.zjm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.zjm"})
public class MyBaseSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyBaseSpringBootApplication.class, args);
    }

}