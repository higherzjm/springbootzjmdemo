package com.zjm.kindsmodels.customermodels1_templateMethod.controller;

import com.zjm.kindsmodels.customermodels1_templateMethod.AbstractService;
import com.zjm.kindsmodels.customermodels1_templateMethod.Myservice;
import com.zjm.kindsmodels.customermodels1_templateMethod.impl.MyServiceImpl1;
import com.zjm.kindsmodels.customermodels1_templateMethod.impl.MyServiceImpl2;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author zhujianming
 */
@Slf4j
public class MyController {
    @Test
    public void test1() {
        Myservice myservice = new AbstractService();
        log.info(myservice.test1("����", new MyServiceImpl1()));
        log.info(myservice.test1("����", new MyServiceImpl2()));
    }

    @Test
    public void test2() {
        Myservice myservice = new MyServiceImpl1();
        log.info(myservice.test12("����"));
        Myservice myservice2 = new MyServiceImpl2();
        log.info(myservice2.test12("����"));
    }
}
