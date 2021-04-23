package com.zjm.kindsmodels.customermodels1.impl;

import com.zjm.kindsmodels.customermodels1.AbstractService;

/**
 * @author zhujianming
 */
public class MyServiceImpl2 extends AbstractService {
    @Override
    public String test2(String msg){

        return  msg+"->MyServiceImpl2:"+this.toString();
    }
}
