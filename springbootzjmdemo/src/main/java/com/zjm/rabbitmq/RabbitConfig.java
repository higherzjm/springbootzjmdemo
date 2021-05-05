package com.zjm.rabbitmq;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class RabbitConfig {


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
    @Bean
    public Queue baseQueue(){
        return new Queue("baseQueue");
    }
 
    /**
     * 声明一个Direct类型的交换机,新生注册交换机
     **/
    @Bean
    DirectExchange directExchange(){
        return new DirectExchange("newStudentsRegister");
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

    public RabbitConfig() {

    }
}
