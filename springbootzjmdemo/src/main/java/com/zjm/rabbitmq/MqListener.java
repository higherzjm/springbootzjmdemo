package com.zjm.rabbitmq;

import com.rabbitmq.client.Channel;
import com.zjm.util.ConstantUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import javax.servlet.ServletContextListener;
import java.io.IOException;

@Component
@Slf4j
public class MqListener implements ServletContextListener {
    @RabbitListener(queues = {ConstantUtil.rabbitMqQueueName})
    public void listener20211102(String content, Message message, Channel channel) {
        try {
            log.info("content:"+content);
            log.info("message:"+message);
            log.info("channel:"+channel);
        }finally {
            try {
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}