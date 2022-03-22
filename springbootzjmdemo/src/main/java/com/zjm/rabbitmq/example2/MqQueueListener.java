package com.zjm.rabbitmq.example2;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import com.zjm.baseapplication.VO.StudentInfo;
import com.zjm.rabbitmq.example1.MqExchangeListener;
import com.zjm.util.ConstantUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;
import java.util.List;

//@Component
@Slf4j
public class MqQueueListener implements ServletContextListener {
    //@Autowired
    private RabbitTemplate rabbitTemplate;
    public static JSONObject sendConfirmRetMsg=new JSONObject();
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //发消息
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean b, String s) {
                log.info("消息发送确认回调: " + correlationData + ",s=" + s + ",b:" + b);
                sendConfirmRetMsg.put("correlationData",correlationData);
                sendConfirmRetMsg.put("s",s);
                sendConfirmRetMsg.put("b",b);
            }
        });
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int i, String s, String s1, String s2) {
                log.info("消息发送返回回调: " + message + ","+i + ","+s+"," +s1+","+s2);
            }
        });
        //开启发送邮件的线程
        new Thread(new SendMessageThread()).start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        //在这里做数据备份操作
    }

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
