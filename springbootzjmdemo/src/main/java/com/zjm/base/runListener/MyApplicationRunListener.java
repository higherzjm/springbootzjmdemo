package com.zjm.base.runListener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * 运行时监听器
 */
@Slf4j
public class MyApplicationRunListener implements SpringApplicationRunListener{
    private final SpringApplication application;
    private final String[] args;

    public MyApplicationRunListener(SpringApplication sa, String[] args) {
        this.application = sa;
        this.args = args;
        log.info("运行时监听器初始化");
    }
    @Override
    public void starting() {
        System.out.println("-------------1----------------starting");
    }

    @Override
    public void environmentPrepared(ConfigurableEnvironment environment) {
        System.out.println("-------------2----------------environmentPrepared");
    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        System.out.println("-------------------3----------------contextPrepared");
    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {
        System.out.println("-------------------4-------------contextLoaded");
    }

    @Override
    public void started(ConfigurableApplicationContext context) {
        System.out.println("------------------5---------------started");
    }

    @Override
    public void running(ConfigurableApplicationContext context) {
        System.out.println("-------------------------6---------------------running");
    }

    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {
        System.out.println("运行时监听器启动失败");
        System.out.println("运行时监听器启动失败");
    }

}
