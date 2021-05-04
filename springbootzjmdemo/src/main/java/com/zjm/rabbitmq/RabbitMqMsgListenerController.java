package com.zjm.rabbitmq;

import com.rabbitmq.client.Channel;
import com.zjm.rabbitmq.example2.FinancingProducer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @author zhujianming
 */
@RequestMapping("/rabbitMpMsgCustomer")
@RestController
@Api(tags = "rabbitmq消息消费者")
@Slf4j
public class RabbitMqMsgListenerController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    //直连模式的多个消费者，会分到其中一个消费者进行消费。类似task模式
    //通过注入RabbitContainerFactory对象，来设置一些属性，相当于task里的channel.basicQos
    @RabbitListener(queues="financingadd")
    public void helloWorldReceive(String message, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel) {

        log.info("financingadd队列接收消息:" +message+","+deliveryTag+","+channel);
        try {
            channel.basicAck(deliveryTag,false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   //工作队列模式
    @RabbitListener(queues="insureadd")
    public void wordQueueReceiveq1(String message) {

        log.info("工作队列模式1 监听器1 : " +message);
    }

    @RabbitListener(queues="insureadd")
    public void wordQueueReceiveq2(String message) {

        log.info("工作队列模式2 监听器2 : " +message);
    }


    //pub/sub模式进行消息监听
    @RabbitListener(queues="moneyadd")
    public void fanoutReceiveq1(String message) {

        log.info("发布订阅模式1received message : " +message);
    }
    @RabbitListener(queues="moneyadd")
    public void fanoutReceiveq2(String message) {

        System.out.println("发布订阅模式2 received message : " +message);
    }


    //Routing路由模式
    @RabbitListener(queues="moneyadd")
    public void routingReceiveq1(String message) {

        System.out.println("Routing路由模式routingReceiveq11111 received message : " +message);
    }
    @RabbitListener(queues="moneyadd")
    public void routingReceiveq2(String message) {

        System.out.println("Routing路由模式routingReceiveq22222 received message : " +message);
    }


    //topic 模式
    //注意这个模式会有优先匹配原则。例如发送routingKey=hunan.IT,那匹配到hunan.*(hunan.IT,hunan.eco),之后就不会再去匹配*.ITd
    @RabbitListener(queues="moneyadd")
    public void topicReceiveq1(String message) {
        System.out.println("Topic模式 topic_sb_mq_q1 received message : " +message);
    }

    @RabbitListener(queues="moneyadd")
    public void topicReceiveq2(String message) {
        System.out.println("Topic模式 topic_sb_mq_q2 received  message : " +message);
    }

    @RabbitListener(queues="moneyadd")
    public void delayReceiveq(String message) {
        System.out.println("延迟队列 dlxQueue received  message : " +message);
    }
}
