package com.zjm.rabbitmq.example2;

import com.zjm.baseapplication.VO.StudentInfo;
import com.zjm.rabbitmq.example1.MqExchangeListener;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author zhujianming
 * @description
 * @date 2022/3/22 14:28
 */
@Slf4j
class SendMessageThread implements Runnable {

    @Override
    public void run() {
        while (1==1){
            List<StudentInfo> studentInfoList= null;
            try {
                studentInfoList = MqExchangeListener.getBlockingDequeInfo();
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
            log.info("向这些学生发送报道的消息"+studentInfoList);
        }
    }
}
