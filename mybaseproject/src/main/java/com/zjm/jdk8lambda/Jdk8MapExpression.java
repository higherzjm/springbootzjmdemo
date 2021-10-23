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
     * computeIfAbsent ��ȡָ��key��ֵ�����û��������Ի��Զ���Ӵ�key��list����ֱ�ӿ��Ը�ֵ
     * ��ָ���Ŀ��Խ��к���ʽ���޸�
     */
    @Test
    public void test1(){
        List<String> list=new ArrayList<>();
        Map<String, List> map = Maps.newHashMap();
        list.add("����");
        list.add("����");
        map.putIfAbsent("list",list);
        list = map.computeIfAbsent("list", (value) -> new ArrayList<String>());
        log.info("computeIfAbsent list:"+list);//-->{ computeIfAbsent list:[����, ����] }

        list = map.computeIfAbsent("list2", (value) -> new ArrayList<String>());
        log.info("computeIfAbsent list2:"+list);
        list.add("World");
        list.add("���");
        log.info("computeIfAbsent list2:"+list);
        log.info("computeIfAbsent map.:"+map);
        log.info("computeIfAbsent map.values:"+map.values());

        Map<String,String> myMap=new HashMap<>();
        String name=myMap.computeIfPresent("name", (key,value) ->value+"="+value+";");
        log.info("name:"+name);
        myMap.putIfAbsent("name","����");
         name=myMap.computeIfPresent("name", (key,value) ->key+"="+value+";");
        log.info("name:"+name);//-->{name:name=����;}
    }
    /**
     * putIfAbsent ������ָ����keyֵ�Ż�put�������˶���if�ж�
     */
    @Test
    public void test2(){
       Map<String,String> map=new HashMap<>();
       map.put("name","����");
       map.put("name","����"); //�Ḳ��ǰ����ͬkeyΪname��ֵ
       log.info("map:"+map);
       map.putIfAbsent("name","����");//���粻����keyΪname�ļ�ֵ�Ż�put��ȥ����Ȼ����
        map.putIfAbsent("age","35");
       log.info("map:"+map);
    }
}
