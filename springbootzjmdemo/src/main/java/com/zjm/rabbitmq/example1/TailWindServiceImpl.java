package com.zjm.rabbitmq.example1;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Service;

@Service
public class TailWindServiceImpl implements MessageListener {


    @Override
    public void onMessage(Message message) {

        byte[] body = message.getBody();
        String s = new String(body);
        System.out.println("接收到消息");
    }

}