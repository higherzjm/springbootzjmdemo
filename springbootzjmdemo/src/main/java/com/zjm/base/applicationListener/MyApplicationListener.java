package com.zjm.base.applicationListener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
@Slf4j
public class MyApplicationListener implements ApplicationListener {
    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        log.info("我的应用监听器被调用");
    }

    public MyApplicationListener() {
        log.info("我的应用监听器被实例化");
    }
}
