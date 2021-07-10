package com.zjm.base.runListener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * spring���м�����
 */
public class MyApplicationRunListener implements SpringApplicationRunListener{
    private final SpringApplication application;
    private final String[] args;

    public MyApplicationRunListener(SpringApplication sa, String[] args) {
        this.application = sa;
        this.args = args;
    }
    @Override
    public void starting() {
        System.out.println("-------------1----------------���п�ʼ");
    }

    @Override
    public void environmentPrepared(ConfigurableEnvironment environment) {
        System.out.println("-------------2----------------����׼������");
    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        System.out.println("-------------------3----------------������׼�����");
    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {
        System.out.println("-------------------4-------------�����ļ��ؽ���");
    }

    @Override
    public void started(ConfigurableApplicationContext context) {
        System.out.println("------------------5---------------����׼����������");
    }

    @Override
    public void running(ConfigurableApplicationContext context) {
        System.out.println("-------------------------6---------------------ϵͳ��������");
    }

    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {
        System.out.println("���п�ʼ");
    }

}
