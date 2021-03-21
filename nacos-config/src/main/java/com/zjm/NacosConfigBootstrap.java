package com.zjm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 * @version 1.0
 **/
@SpringBootApplication
@RestController
public class NacosConfigBootstrap {
    public static void main(String[] args) {
        SpringApplication.run(NacosConfigBootstrap.class, args);
    }

    @Autowired
    ConfigurableApplicationContext applicationContext;
    //通过value注解读取配置信息
    @Value("${common.bootstrap}")
    private String bootstrapValue;

    //http://localhost:8082/configs
    @GetMapping("/configs")
    public String getConfigs() {
        //读取配置信息
        return applicationContext.getEnvironment().getProperty("common.name");
    }
    //http://localhost:8082/configs2
    @GetMapping(value = "/configs2")
    public String getConfigs2() {
        String name = applicationContext.getEnvironment().getProperty("common.name");
        String age = applicationContext.getEnvironment().getProperty("common.age");
        String address = applicationContext.getEnvironment().getProperty("common.address");
        String birthday = applicationContext.getEnvironment().getProperty("common.birthday");
        String fullName = applicationContext.getEnvironment().getProperty("common.fullname");
        String a1 = applicationContext.getEnvironment().getProperty("common.a1");
        String a2 = applicationContext.getEnvironment().getProperty("common.a2");
        return bootstrapValue + "+" + name + "+" + age + "+" + address + "+" + birthday + "+" + fullName + "+ " + a1 + "+" + a2;
    }
}
