package com.zjm;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author zhujianming
 */
@Slf4j
public class MySortedMap {

    @Test
    public void test1(){
        SortedMap<Integer, String> treemap =  new TreeMap<>();
        // populating tree map
        treemap.put(2, "two");
        treemap.put(1, "one");
        treemap.put(7, "six");
        treemap.put(3, "three");
        treemap.put(6, "six");
        treemap.put(5, "five");
        log.info("获取大于等于指定key的子map:"+treemap.tailMap(3));
    }
}
