package com.zjm.myxxljob;

import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SpringBootHandler{

    @XxlJob("springBootHandler")
    public void springBootHandler() throws Exception {
        System.out.println("springBootHandler:"+ LocalDateTime.now());

    }
}
