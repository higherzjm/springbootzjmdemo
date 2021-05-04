package com.zjm.rabbitmq.example1;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
 
    @Autowired
    private TailWindServiceImpl tailWindService;
    @Autowired
    private ConnectionFactory connectionFactory;
 
   @Bean(name = "primarySimpleMessageListenerContainer")
    public SimpleMessageListenerContainer primaryMessageListenerContainer() {
        SimpleMessageListenerContainer container = new         
        SimpleMessageListenerContainer(connectionFactory);
        container.setDefaultRequeueRejected(false);
        container.setAcknowledgeMode(AcknowledgeMode.AUTO);
        container.setMessageListener(tailWindService);
        container.start();
        return container;
    }
 
}
