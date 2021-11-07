package com.zjm.xxljob;

//import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
/**
 * @Description: XxlJob 定时器
 * @Author: zhujianming
 * @Date: 2021/6/6
 * @param:
 *
 **/
@Component
public class SpringBootHandler{

   // @XxlJob("xxlJobSpringBootHandler")
    public void springBootHandler() throws Exception {
        System.out.println("xxlJob任务执行->springBootHandler:"+ LocalDateTime.now());

    }
}
