package com.vv.springboot.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/*
   延迟队列 TTL+死信队列
*/
@Configuration
public class DelayConfig {

    /**
     * 声明死信队列
     * @return DirectExchange
     */
    @Bean
    public DirectExchange dlxExchange() {
        return new DirectExchange("dlxExchange");
    }

    /**
     * 声明死信队列
     * @return Queue
     */
    @Bean
    public Queue dlxQueue() {
        return new Queue("dlxQueue");
    }

    /**
     * 绑定死信队列到死信交换机
     * @return Binding
     */
    @Bean
    public Binding binding() {
        return BindingBuilder.bind(dlxQueue())
                .to(dlxExchange())
                .with("dlxRoutingKey");
    }

    /**
     * 声明订单业务交换机
     * @return DirectExchange
     */
    @Bean
    public DirectExchange orderExchange() {
        return new DirectExchange("orderExchange");
    }

    /**
     * 声明订单业务队列
     * @return Queue
     */
    @Bean
    public Queue orderQueue() {
        Map<String,Object> arguments = new HashMap<>(2);
        // 绑定该队列到私信交换机
        arguments.put("x-dead-letter-exchange","dlxExchange");
        arguments.put("x-dead-letter-routing-key","dlxRoutingKey");
        return new Queue("orderQueue",true,false,false,arguments);
    }

    /**
     * 绑定订单队列到订单交换机
     * @return Binding
     */
    @Bean
    public Binding orderBinding() {
        return BindingBuilder.bind(orderQueue())
                .to(orderExchange())
                .with("orderRoutingKey");

    }
}
