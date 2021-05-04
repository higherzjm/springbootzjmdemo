package com.zjm.rabbitmq.example1;

import com.rabbitmq.client.AMQP;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
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
@Service
public class DyCreateQueueService {

    @Autowired
    private SimpleMessageListenerContainer messageListenerContainer;
    /**
     * 设置路由key
     */
    public String ROUTINGKEY = "myroutingkey2";
    public String EXCHANGE = "myexchange2";

    //创建队列，绑定交换机
    public String dyCreateQueue() {
        Long second = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
        String queueName="queueName"+second;
        Queue queue = new Queue(queueName);
        BindingBuilder.bind(queue).to(new DirectExchange(EXCHANGE, true, true)).with(ROUTINGKEY);
        messageListenerContainer.addQueueNames(queueName);
        messageListenerContainer.start();
        return queueName;
    }
}
