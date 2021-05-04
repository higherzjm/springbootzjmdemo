package com.zjm.rabbitmq.example1;

import com.rabbitmq.client.AMQP;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    @Qualifier("primarySimpleMessageListenerContainer")
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
        ROUTINGKEY=ROUTINGKEY+second;
        EXCHANGE=EXCHANGE+second;
        Queue queue = new Queue(queueName,true);
        DirectExchange directExchange=new DirectExchange(EXCHANGE, true, true);
        Binding binding=BindingBuilder.bind(queue).to(directExchange).with(ROUTINGKEY);
        messageListenerContainer.addQueues(queue);
        messageListenerContainer.start();
        return queueName;
    }
}
