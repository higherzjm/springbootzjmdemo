package com.zjm.rabbitmq;

import com.zjm.rabbitmq.example2.FinancingProducer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhujianming
 */
@RequestMapping("/rabbitMp")
@RestController
@Api(tags = "rabbitmq消息队列")
public class RabbitController {
    @Autowired
    private FinancingProducer financingProducer;
    /**
     * 测试方法
     *
     * @return
     */
    @GetMapping("/testSendMsg")
    @ApiOperation(value = "测试2-消息发送", notes = "测试2-消息发送")
    public String testSendMsg() {
        financingProducer.sendMessageAdd();
        financingProducer.sendMessageUpdate();
        financingProducer.sendMessageDel();
        return "发送成功！";
    }
}
