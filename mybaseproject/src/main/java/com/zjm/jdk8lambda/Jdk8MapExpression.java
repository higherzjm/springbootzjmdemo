package com.zjm.jdk8lambda;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class Jdk8MapExpression {
    /**
     * computeIfAbsent 获取指定key的值，如果没有这个可以会自动添加此key，list集合直接可以赋值
     * 对指定的可以进行函数式的修改
     */
    @Test
    public void test1(){
        List<String> list=new ArrayList<>();
        Map<String, List> map = Maps.newHashMap();
        list.add("张三");
        list.add("李四");
        map.putIfAbsent("list",list);
        list = map.computeIfAbsent("list", (value) -> new ArrayList<String>());
        log.info("computeIfAbsent list:"+list);//-->{ computeIfAbsent list:[张三, 李四] }

        list = map.computeIfAbsent("list2", (value) -> new ArrayList<String>());
        log.info("computeIfAbsent list2:"+list);
        list.add("World");
        list.add("你好");
        log.info("computeIfAbsent list2:"+list);
        log.info("computeIfAbsent map.:"+map);
        log.info("computeIfAbsent map.values:"+map.values());

        Map<String,String> myMap=new HashMap<>();
        String name=myMap.computeIfPresent("name", (key,value) ->value+"="+value+";");
        log.info("name:"+name);
        myMap.putIfAbsent("name","张三");
         name=myMap.computeIfPresent("name", (key,value) ->key+"="+value+";");
        log.info("name:"+name);//-->{name:name=张三;}
    }
    /**
     * putIfAbsent 不存在指定的key值才会put，避免了多余if判断
     */
    @Test
    public void test2(){
       Map<String,String> map=new HashMap<>();
       map.put("name","张三");
       map.put("name","李四"); //会覆盖前面相同key为name的值
       log.info("map:"+map);
       map.putIfAbsent("name","王五");//假如不存在key为name的键值才会put进去，不然不会
        map.putIfAbsent("age","35");
       log.info("map:"+map);
    }
}
