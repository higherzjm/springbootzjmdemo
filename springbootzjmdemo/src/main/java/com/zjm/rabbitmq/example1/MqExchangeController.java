package com.zjm.rabbitmq.example1;

import com.alibaba.fastjson.JSONObject;
import com.zjm.baseapplication.VO.StudentInfo;
import com.zjm.rabbitmq.example2.MqQueueListener;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zhujianming
 */
@RequestMapping("/rabbitMpMsgProducerController")
@RestController
@Api(tags = "rabbitmq消息中间件使用")
@Slf4j
public class MqExchangeController {
    //@Autowired
    private RabbitTemplate rabbitTemplate;
    //@Autowired
    private AmqpTemplate amqpTemplate;

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
        log.info("发送回调的消息:" + MqQueueListener.sendConfirmRetMsg);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("发送回调的消息:" + MqQueueListener.sendConfirmRetMsg);
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
        log.info("发送回调的消息:" + MqQueueListener.sendConfirmRetMsg);
    }


}
