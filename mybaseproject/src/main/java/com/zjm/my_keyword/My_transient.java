package com.zjm.my_keyword;

import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.Map;

/**
 * @author zhujianming
 * @description
 * @date 2022/2/28 15:12
 */
@Slf4j
public class My_transient implements Serializable {
    private transient Map<String,Object> nameMap;

    public void test(){
        log.info("nameMap:",nameMap);
    }
}
