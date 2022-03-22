package com.zjm.rabbitmq.example2;

import com.zjm.util.ConstantUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

/**
 * @author zhujianming
 */
@RequestMapping("/rabbitMpMsgProducerController")
@RestController
@Api(tags = "rabbitmq消息中间件使用")
@Slf4j
public class MqQueueController {
    //@Autowired
    private RabbitTemplate rabbitTemplate;
    //@Autowired
    private AmqpTemplate amqpTemplate;

    @GetMapping("/easyTest20211102")
    @ApiOperation(value = "简单测试")
    public String  easyTest(){
        System.out.println("向消息中间件发送消息");
        rabbitTemplate.convertAndSend(ConstantUtil.rabbitMqQueueName, "向消息中间件发送消息");
        return "发送成功";
    }

    @ApiOperation(value = "消费发送并回调确认", notes = "消费发送并回调确认")
    @GetMapping(value = "/baseQueueMsgSend/{msg}")
    public Object baseQueueMsgSend(@ApiParam(name = "msg", value = "发送的消息", required = true)
                                   @PathVariable("msg") String msg) throws AmqpException, UnsupportedEncodingException {
        //设置部分请求参数
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN);
        rabbitTemplate.send("baseQueue", new Message(msg.getBytes("UTF-8"), messageProperties));
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("发送回调的消息:" + MqQueueListener.sendConfirmRetMsg);
        return "发送的消息: " + msg;
    }

}
