package com.zjm.rabbitmq.example1;

import com.rabbitmq.client.AMQP;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.amqp.core.Queue;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * @author zhujianming
 */
//@Service
public class DyCreateQueueService {
    @Resource
   // @Qualifier("primaryRabbitAdmin")
    private RabbitAdmin rabbitAdmin;
    @Autowired
    private SimpleMessageListenerContainer messageListenerContainer;


    //创建队列，绑定交换机
    public String dyCreateQueue() {
        Long second = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
        String queueName="queueName"+second;
        Queue queue = new Queue(queueName);
        FanoutExchange exchange = new
                FanoutExchange("fanoutExchangeName"+second);
        rabbitAdmin.declareQueue(queue);
        rabbitAdmin.declareExchange(exchange);
        rabbitAdmin.declareBinding(BindingBuilder.bind(queue).to(exchange));
        messageListenerContainer.addQueueNames(queueName);

        messageListenerContainer.start();
        return queueName;
    }
}
