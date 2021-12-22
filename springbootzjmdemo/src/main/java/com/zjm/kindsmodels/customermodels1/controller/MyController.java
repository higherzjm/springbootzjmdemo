package com.zjm.kindsmodels.customermodels1.controller;

import com.zjm.kindsmodels.customermodels1.AbstractService;
import com.zjm.kindsmodels.customermodels1.Myservice;
import com.zjm.kindsmodels.customermodels1.impl.MyServiceImpl1;
import com.zjm.kindsmodels.customermodels1.impl.MyServiceImpl2;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author zhujianming
 * 模板方法模式
 */
@Slf4j
public class MyController {
    @Test
    public void test1() {
        Myservice myservice = new AbstractService();
        log.info(myservice.test1("张三", new MyServiceImpl1()));
        log.info(myservice.test1("张三", new MyServiceImpl2()));
    }

    @Test
    public void test2() {
        Myservice myservice = new MyServiceImpl1();
        log.info(myservice.test12("张三"));
        Myservice myservice2 = new MyServiceImpl2();
        log.info(myservice2.test12("张三"));
    }
}
