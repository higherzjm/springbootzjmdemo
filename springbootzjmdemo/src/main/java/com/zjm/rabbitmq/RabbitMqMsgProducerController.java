package com.zjm.rabbitmq;

import com.zjm.rabbitmq.example1.DyCreateQueueService;
import com.zjm.rabbitmq.example2.FinancingProducer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

/**
 * @author zhujianming
 */
@RequestMapping("/rabbitMpMsgProducer")
@RestController
@Api(tags = "rabbitmq消息中间件使用 ")
@Slf4j
public class RabbitMqMsgProducerController {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private FinancingProducer financingProducer;
    @Autowired
    private DyCreateQueueService dyCreateQueueService;
    /**
     * 创建队列并发送消息
     * @return
     */
    @GetMapping("/dyCreateQueue")
    @ApiOperation(value = "动态创建队列并发送消息", notes = "创建队列并发送消息")
    public String dyCreateQueue() {
        return "创建成功:"+ dyCreateQueueService.dyCreateQueue();
    }
    /**
     * 创建队列并发送消息
     * @return
     */
    @GetMapping("/createQueueAndSendMsg")
    @ApiOperation(value = "创建队列并监听发送消息", notes = "创建队列并监听发送消息")
    public String testSendMsg() {
        financingProducer.sendMessageAdd();
        financingProducer.sendMessageUpdate();
        financingProducer.sendMessageDel();
        return "创建并发送成功！";
    }
    //helloWorld 直连模式
   @ApiOperation(value="helloWorld发送接口",notes="直接发送到队列")
    @GetMapping(value="/helloWorldSend/{msg}")
    public Object helloWorldSend(@ApiParam(name = "msg", value = "发送的消息", required = true)
                                     @PathVariable("msg") String msg) throws AmqpException, UnsupportedEncodingException {
        //设置部分请求参数
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN);

        //发消息
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean b, String s) {
                log.info("消息发送确认回调: " + correlationData + ",s=" + s + ",b:" + b);
            }
        });
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int i, String s, String s1, String s2) {
                log.info("消息发送返回回调: " + message + ","+i + ","+s+"," +s1+","+s2);
            }
        });
        rabbitTemplate.send("financingadd",new Message(msg.getBytes("UTF-8"),messageProperties));
        return "message sended : "+msg;
    }


    //工作队列模式
    @ApiOperation(value="workqueue发送接口",notes="发送到所有监听该队列的消费")
    @GetMapping(value="/workqueueSend/{msg}")
    public Object workqueueSend(@ApiParam(name = "msg", value = "发送的消息", required = true)
                                    @PathVariable("msg") String msg) throws AmqpException, UnsupportedEncodingException {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN);
        //制造多个消息进行发送操作fanout
        for (int i = 0; i <10 ; i++) {
            rabbitTemplate.send("insureadd",  new Message(msg.getBytes("UTF-8"),messageProperties));
        }
        return "message sended : "+msg;
    }


    // pub/sub 发布订阅模式   交换机类型 fanout
    @ApiOperation(value="fanout发送接口",notes="发送到fanoutExchange。消息将往该exchange下的所有queue转发")
    @GetMapping(value="/fanoutSend/{msg}")
    public Object fanoutSend(@ApiParam(name = "msg", value = "发送的消息", required = true)
                                 @PathVariable("msg") String msg) throws AmqpException, UnsupportedEncodingException {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN);
        //fanout模式只往exchange里发送消息。分发到exchange下的所有queue
        rabbitTemplate.send("myexchange", "", new Message(msg.getBytes("UTF-8"),messageProperties));
        return "message sended : "+msg;
    }


    //routing路由工作模式  交换机类型 direct
    @ApiOperation(value="direct发送接口",notes="发送到directExchange。exchange转发消息时，会往routingKey匹配的queue发送")
    @GetMapping(value="/directSend/{msg}")
    public Object routingSend(String routingKey,@ApiParam(name = "msg", value = "发送的消息", required = true)
    @PathVariable("msg") String msg) throws AmqpException, UnsupportedEncodingException {

        if(null == routingKey) {
            routingKey="china.changsha";
        }
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN);
        //fanout模式只往exchange里发送消息。分发到exchange下的所有queue
        rabbitTemplate.send("myexchange", routingKey, new Message(msg.getBytes("UTF-8"),messageProperties));
        return "message sended : routingKey >"+routingKey+";message > "+msg;
    }

    //topic 工作模式   交换机类型 topic
    @ApiOperation(value="topic发送接口",notes="发送到topicExchange。exchange转发消息时，会往routingKey匹配的queue发送，*代表一个单词，#代表0个或多个单词。")
    @GetMapping(value="/topicSend/{msg}")
    public Object topicSend(String routingKey,@ApiParam(name = "msg", value = "发送的消息", required = true)
    @PathVariable("msg") String msg) throws AmqpException, UnsupportedEncodingException {

        if(null == routingKey) {
            routingKey="changsha.kf";
        }
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN);
        //fanout模式只往exchange里发送消息。分发到exchange下的所有queue
        rabbitTemplate.send("myexchange", routingKey, new Message(msg.getBytes("UTF-8"),messageProperties));
        return "message sended : routingKey >"+routingKey+";message > "+msg;
    }

    //发送带有过期时间的消息
    @GetMapping("/sendDlx/{msg}")
    public Object sendDlx(String routingKey,@ApiParam(name = "msg", value = "发送的消息", required = true)
    @PathVariable("msg") String msg) throws AmqpException, UnsupportedEncodingException {

        if(null == routingKey) {
            routingKey="orderRoutingKey";
        }
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN);
        messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
        messageProperties.setExpiration("10000");

        //fanout模式只往exchange里发送消息。分发到exchange下的所有queue
        rabbitTemplate.send("myexchange", routingKey, new Message(msg.getBytes("UTF-8"),messageProperties));

        return "message sended : routingKey >"+routingKey+";message > "+msg;
    }
}
