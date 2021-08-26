package com.zjm.jdk8lambda;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class Jdk8MapExpression {
    /**
     * computeIfAbsent 定义指定的key名称
     */
    @Test
    public void test1(){
        List list;
        Map<String, List> map = Maps.newHashMap();
        list = map.computeIfAbsent("list", (key) -> new ArrayList<String>());
        list.add("World");
        list.add("你好");
        log.info("computeIfAbsent list:"+list);
        log.info("computeIfAbsent map.:"+map);
        log.info("computeIfAbsent map.values:"+map.values());
    }
}
