package com.zjm.kindsmodels.customermodels1_templateMethod.impl;

import com.zjm.kindsmodels.customermodels1_templateMethod.AbstractService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhujianming
 */
@Slf4j
public class MyServiceImpl1 extends AbstractService {
    @Override
    public String test2(String msg){

        return  msg+"->MyServiceImpl1:"+this.toString();
    }
}
