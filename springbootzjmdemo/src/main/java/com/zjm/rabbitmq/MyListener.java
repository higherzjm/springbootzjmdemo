package com.zjm.rabbitmq;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
@Slf4j
public class MyListener implements ServletContextListener {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    public static JSONObject sendConfirmRetmsg=new JSONObject();
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //发消息
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean b, String s) {
                log.info("消息发送确认回调: " + correlationData + ",s=" + s + ",b:" + b);
                sendConfirmRetmsg.put("correlationData",correlationData);
                sendConfirmRetmsg.put("s",s);
                sendConfirmRetmsg.put("b",b);
            }
        });
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int i, String s, String s1, String s2) {
                log.info("消息发送返回回调: " + message + ","+i + ","+s+"," +s1+","+s2);
            }
        });
    }
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        //在这里做数据备份操作
    }
}