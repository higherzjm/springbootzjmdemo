package com.zjm.rabbitmq.example1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhujianming
 * @description
 * @date 2022/3/22 14:15
 */
@Configuration
@Slf4j
public class MqExchangeConfig {
    /**
     * 声明一个Direct类型的交换机,新生注册交换机
     **/
    @Bean
    DirectExchange directExchange(){
        return new DirectExchange("newStudentsRegister");
    }
    /**
     *  创建队列
     **/
    @Bean
    public Queue newStudentListsQueue(){
        return new Queue("newStudentListsQueue");
    }

    @Bean
    public Queue addNewStudentNoticeQueue(){
        return new Queue("addNewStudentNoticeQueue");
    }

    /**
     * 将队列绑定到指定的交换机并声明路由
     */
    @Bean
    Binding bindExchangeA(Queue newStudentListsQueue, DirectExchange directExchange){
        return BindingBuilder.bind(newStudentListsQueue).to(directExchange).with("newStudentLists");
    }

    @Bean
    Binding bindExchangeB(Queue addNewStudentNoticeQueue,DirectExchange directExchange){
        return BindingBuilder.bind(addNewStudentNoticeQueue).to(directExchange).with("addNewStudentNotice");
    }
}
