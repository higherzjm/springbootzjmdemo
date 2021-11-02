package com.zjm.rabbitmq;

import com.alibaba.fastjson.JSONObject;
import com.zjm.base.StudentInfo;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author zhujianming
 */
@RequestMapping("/rabbitMpMsgProducerController")
@RestController
@Api(tags = "rabbitmq消息中间件使用")
@Slf4j
public class MqController {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    //@Autowired
    private AmqpTemplate amqpTemplate;

    @GetMapping("/easyTest20211102")
    @ApiOperation(value = "简单测试")
    public void easyTest(){
        System.out.println("向消息中间件发送消息");
        rabbitTemplate.convertAndSend(ConstantUtil.rabbitMqQueueName, "向消息中间件发送消息");
    }
    /**
     * 向指定交换机和路由下发送消息(教务处通知班主任新注册的学生)
     */
    @PostMapping("/addNewStudents")
    @ApiOperation(value = "添加的新年学生列表", notes = "添加的新年学生列表")
    public void addNewStudents(@RequestBody @Validated List<StudentInfo> studentInfoList) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("newStudents", studentInfoList);

        log.info("添加列表的新学生 : " + jsonObject.toJSONString());
        rabbitTemplate.convertAndSend("newStudentsRegister", "newStudentLists", jsonObject.toJSONString());
        log.info("发送回调的消息:" + RabbitMqConfirmSendListener.sendConfirmRetMsg);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("发送回调的消息:" + RabbitMqConfirmSendListener.sendConfirmRetMsg);
    }

    @GetMapping("/sendNotice")
    @ApiOperation(value = "向教务处发出添加新学生的通知", notes = "向教务处发出添加新学生的通知")
    public void sendMessage2() {
        String message = "领导您好,本次添加2名新学生!";
        rabbitTemplate.convertAndSend("newStudentsRegister", "addNewStudentNotice", message);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("发送回调的消息:" + RabbitMqConfirmSendListener.sendConfirmRetMsg);
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
        log.info("发送回调的消息:" + RabbitMqConfirmSendListener.sendConfirmRetMsg);
        return "发送的消息: " + msg;
    }

}
