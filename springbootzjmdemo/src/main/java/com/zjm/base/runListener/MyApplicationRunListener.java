package com.zjm.base.runListener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * spring运行监听器
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
        System.out.println("-------------1----------------运行开始");
    }

    @Override
    public void environmentPrepared(ConfigurableEnvironment environment) {
        System.out.println("-------------2----------------环境准备结束");
    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        System.out.println("-------------------3----------------上下文准备完成");
    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {
        System.out.println("-------------------4-------------上下文加载结束");
    }

    @Override
    public void started(ConfigurableApplicationContext context) {
        System.out.println("------------------5---------------运行准备工作结束");
    }

    @Override
    public void running(ConfigurableApplicationContext context) {
        System.out.println("-------------------------6---------------------系统正在运行");
    }

    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {
        System.out.println("运行开始");
    }

}
