package com.zjm.base.Initializer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 应用上下文初始
 */
@Slf4j
public class MyApplicationContextInitializer
        implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        log.info("=------------------------我的应用上下文初始化");
    }

    public MyApplicationContextInitializer() {
        log.info("我的应用上下午初始化器被实例化");
    }

}
