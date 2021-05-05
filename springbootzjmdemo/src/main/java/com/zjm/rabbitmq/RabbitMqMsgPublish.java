package com.zjm.rabbitmq;

import com.alibaba.fastjson.JSONObject;
import com.zjm.base.StudentInfo;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

/**
 * @author zhujianming
 */
@RequestMapping("/rabbitMpMsgProducer")
@RestController
@Api(tags = "rabbitmq消息中间件使用")
@Slf4j
public class RabbitMqMsgPublish {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private AmqpTemplate amqpTemplate;


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


    /**
     * 向指定交换机和路由下发送消息
     */
    @GetMapping("/addNewStudents")
    @ApiOperation(value = "添加的新年学生列表", notes = "添加的新年学生列表")
    public void sendMessage() {
        List<StudentInfo> studentInfoList = Arrays.asList(StudentInfo.builder().name("张三").age(20).build(),
                StudentInfo.builder().name("李四").age(30).build());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("newStudents", studentInfoList);

        log.info("添加列表的新学生 : " + jsonObject.toJSONString());
        amqpTemplate.convertAndSend("newStudentsRegister", "newStudentLists", jsonObject.toJSONString());
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("发送回调的消息:" + RabbitMqConfirmSendListener.sendConfirmRetMsg);
    }

    @GetMapping("/sendNotice")
    @ApiOperation(value = "向教务处发出添加新学生的通知", notes = "向教务处发出添加新学生的通知")
    public void sendMessage2() {
        String message = "领导您好,本次添加2名新学生!";
        amqpTemplate.convertAndSend("newStudentsRegister", "addNewStudentNotice", message);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("发送回调的消息:" + RabbitMqConfirmSendListener.sendConfirmRetMsg);
    }
}
