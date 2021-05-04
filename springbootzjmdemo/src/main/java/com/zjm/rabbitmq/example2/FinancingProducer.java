package com.zjm.rabbitmq.example2;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author chezhenqi
 * @date 2019/12/24 星期二
 * @time 15:28
 * @description higerpoint
 */
@Component
public class FinancingProducer {
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private RabbitConfig rabbitConfig;

    public void sendMessageAdd() {
        rabbitTemplate.convertAndSend(rabbitConfig.getQueueFinancingAdd(), "向financingAdd队列发送消息");
    }

    public void sendMessageUpdate() {
        rabbitTemplate.convertAndSend(rabbitConfig.getQueueFinancingUpdate(), "向financingUpdate队列发送消息");
    }

    public void sendMessageDel() {
        rabbitTemplate.convertAndSend(rabbitConfig.getQueueFinancingDel(), "向financingDel队列发送消息");
    }
}