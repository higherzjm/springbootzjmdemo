package com.zjm.base.applicationListener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
@Slf4j
public class MyApplicationListener implements ApplicationListener {
    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        log.info("�ҵ�Ӧ�ü�����������");
    }

    public MyApplicationListener() {
        log.info("�ҵ�Ӧ�ü�������ʵ����");
    }
}
