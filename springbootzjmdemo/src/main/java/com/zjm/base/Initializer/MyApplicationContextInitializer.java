package com.zjm.base.Initializer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Ӧ�������ĳ�ʼ
 */
@Slf4j
public class MyApplicationContextInitializer
        implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        log.info("=------------------------�ҵ�Ӧ�������ĳ�ʼ��");
    }

    public MyApplicationContextInitializer() {
        log.info("�ҵ�Ӧ���������ʼ������ʵ����");
    }

}
