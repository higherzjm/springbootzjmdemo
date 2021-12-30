package com.zjm.kindsmodels.customermodels1_templateMethod.impl;

import com.zjm.kindsmodels.customermodels1_templateMethod.AbstractService;

/**
 * @author zhujianming
 */
public class MyServiceImpl2 extends AbstractService {
    @Override
    public String test2(String msg){

        return  msg+"->MyServiceImpl2:"+this.toString();
    }
}
