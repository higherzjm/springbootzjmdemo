package com.zjm.rabbitmq;

import com.zjm.baseapplication.VO.StudentInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 消息发送线程
 */
@Slf4j
public class SendMessageThread implements Runnable {

    @Override
    public void run() {
     while (true){
         List<StudentInfo> studentInfoList= null;
         try {
             studentInfoList = RabbitMqStudentHeadmasterListener.getBlockingDequeInfo();
         } catch (InterruptedException e) {
             e.printStackTrace();
         }
         log.info("向这些学生发送报道的消息"+studentInfoList);
     }
    }
}
