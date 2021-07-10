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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author zhujianming
 */
@RequestMapping("/rabbitMpMsgProducerController")
@RestController
@Api(tags = "rabbitmq��Ϣ�м��ʹ��")
@Slf4j
public class RabbitMqLeaderControllerStudent {
    //@Autowired
    private RabbitTemplate rabbitTemplate;
    //@Autowired
    private AmqpTemplate amqpTemplate;


    /**
     * ��ָ����������·���·�����Ϣ(����֪ͨ��������ע���ѧ��)
     */
    @PostMapping("/addNewStudents")
    @ApiOperation(value = "��ӵ�����ѧ���б�", notes = "��ӵ�����ѧ���б�")
    public void addNewStudents(@RequestBody @Validated List<StudentInfo> studentInfoList) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("newStudents", studentInfoList);

        log.info("����б����ѧ�� : " + jsonObject.toJSONString());
        rabbitTemplate.convertAndSend("newStudentsRegister", "newStudentLists", jsonObject.toJSONString());
        log.info("���ͻص�����Ϣ:" + RabbitMqConfirmSendListener.sendConfirmRetMsg);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("���ͻص�����Ϣ:" + RabbitMqConfirmSendListener.sendConfirmRetMsg);
    }

    @GetMapping("/sendNotice")
    @ApiOperation(value = "����񴦷��������ѧ����֪ͨ", notes = "����񴦷��������ѧ����֪ͨ")
    public void sendMessage2() {
        String message = "�쵼����,�������2����ѧ��!";
        rabbitTemplate.convertAndSend("newStudentsRegister", "addNewStudentNotice", message);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("���ͻص�����Ϣ:" + RabbitMqConfirmSendListener.sendConfirmRetMsg);
    }

    @ApiOperation(value = "���ѷ��Ͳ��ص�ȷ��", notes = "���ѷ��Ͳ��ص�ȷ��")
    @GetMapping(value = "/baseQueueMsgSend/{msg}")
    public Object baseQueueMsgSend(@ApiParam(name = "msg", value = "���͵���Ϣ", required = true)
                                   @PathVariable("msg") String msg) throws AmqpException, UnsupportedEncodingException {
        //���ò����������
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN);
        rabbitTemplate.send("baseQueue", new Message(msg.getBytes("UTF-8"), messageProperties));
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("���ͻص�����Ϣ:" + RabbitMqConfirmSendListener.sendConfirmRetMsg);
        return "���͵���Ϣ: " + msg;
    }
}
