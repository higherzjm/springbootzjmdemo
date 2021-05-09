package com.zjm.rabbitmq;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import com.zjm.base.StudentInfo;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author zhujianming
 */
@RequestMapping("/rabbitMqMsgListener")
@RestController
@Api(tags = "rabbitmq消息监听消费者")
@Slf4j
public class RabbitMqStudentHeadmasterListener {

    private static BlockingDeque<List<StudentInfo>> blockingDeque = new LinkedBlockingDeque<>();

    @RabbitListener(queues = "newStudentListsQueue")
    @RabbitHandler
    public void processA(String message) {
        JSONObject jsonObject = JSONObject.parseObject(message);
        List<StudentInfo> studentInfoList = (List<StudentInfo>) jsonObject.get("newStudents");
        log.info("教务处所收到的新学生列表: " + studentInfoList);
        blockingDeque.add(studentInfoList);
    }
    public static List<StudentInfo> getBlockingDequeInfo() throws InterruptedException {
        return blockingDeque.take();
    }

    @RabbitListener(queues = "addNewStudentNoticeQueue")
    @RabbitHandler
    public void processB1(String message) {

        log.info("教务处接收到的消息: " + message);
    }

    //直连模式的多个消费者，会分到其中一个消费者进行消费。类似task模式
    //通过注入RabbitContainerFactory对象，来设置一些属性，相当于task里的channel.basicQos
    @RabbitListener(queues = "baseQueue")
    public void baseQueue(String message, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel) {

        log.info("baseQueue队列接收消息:" + message + "," + deliveryTag + "," + channel);
      /*  try {
            channel.basicAck(deliveryTag,false);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}
