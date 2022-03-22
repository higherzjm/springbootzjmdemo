package com.zjm.rabbitmq.example2;

import com.alibaba.fastjson.JSONObject;
import com.zjm.util.ConstantUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class QueueConfig {

    @Bean
    public Queue mqQueue_20211102() {
        return new Queue(ConstantUtil.rabbitMqQueueName);
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
    @Bean
    public Queue baseQueue(){
        return new Queue("baseQueue");
    }
 


}
