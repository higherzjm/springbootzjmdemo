package com.zjm.springframework.singleton_prototype;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Order(3)
public class SayHello extends AbstractService {

    @Override
    public void say() {

        super.say();

        log.info("hello:"+this);
    }

    public SayHello() {
        log.info("初始化sayHello");
    }
}