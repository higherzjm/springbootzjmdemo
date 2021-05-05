package com.zjm.rabbitmq.mainTest;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 生产者
 */
@Slf4j
public class Send {

    private static final String EXCHANGE_NAME = "test_exchange_fanout";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 获取连接
        Connection connection = ConnectionUtil.getConnection();
        // 从连接开一个通道
        Channel channel = connection.createChannel();
        // 声明一个direct路由交换机
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);


        // 发送info路由键消息
        String infoMessage = "向info路由发送消息3";
        channel.basicPublish(EXCHANGE_NAME, "info", null, infoMessage.getBytes());
        System.out.println(" [x] Sent routing info message : '" + infoMessage + "'");

        // 发送error路由键消息
        String errorMessage = "向error路由发送消息3";
        channel.basicPublish(EXCHANGE_NAME, "error", null, errorMessage.getBytes());
        System.out.println(" [x] Sent routing error message :  '" + errorMessage + "'");

        channel.close();
        connection.close();
        log.info("消息发放完毕");

    }
}
