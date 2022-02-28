package com.zjm.my_keyword;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author zhujianming
 * @description
 * @date 2022/2/28 15:12
 */
public class My_ThreadLocal {
    private ThreadLocal<Map<String,Object>>  nameMap=new ThreadLocal<>();
    public void test(){
        Map<String,Object> map= Maps.newConcurrentMap();
        map.put("name","张三");
        //设置值
        nameMap.set(map);
        //读取值
        map=nameMap.get();
        //删除值
        nameMap.remove();
    }

}
