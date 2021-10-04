package com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
//@SpringBootApplication(exclude = {RabbitAutoConfiguration.class})//不加载RabbitAutoConfiguration动态配置类，避免一直尝试连接rabbitmq
//, DataSourceAutoConfiguration.class
@ServletComponentScan
/**
 *  没有这个配置事务也可以正常运行,
 *  实例化被注入mapper的service时会做动态化代理绑定，所以mapper有被注入就会被动态化代理实例化
 */
//@MapperScan("com.zjm.springtransaction.mapper")
public class MySpringBootApplication {

    /**
     * druid: http://localhost:8081/druid/login.html  admin/123456
     * swagger： http://localhost:8081/swagger-ui.html
     **/
    public static void main(String[] args) {
        SpringApplication.run(MySpringBootApplication.class, args);
    }

}