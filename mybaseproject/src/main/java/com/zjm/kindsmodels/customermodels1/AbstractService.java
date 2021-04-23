package com.zjm.kindsmodels.customermodels1;


/**
 * @author zhujianming
 */
public class AbstractService implements Myservice {
    private AbstractService abstractService;

    @Override
    public String test1(String name, AbstractService abstractService) {
        this.abstractService = abstractService;
        return abstractService.test2(name);
    }
    @Override
    public String test12(String name) {
        //自动会调用到该抽象类的继承类
        return test2(name);
    }

    public String test2(String msg) {
        return msg;
    }
}
