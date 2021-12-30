package com.zjm.kindsmodels.customermodels2_strategyfactory;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 *  高校工厂
 */
public class UniversityFactory {
    public static Map<String,IUniversityManage> universityMap= Maps.newHashMap();

    /**
     * 通过工厂获取指定高校
     * @param name
     * @return
     */
    public static IUniversityManage getFactory(String name){
      return universityMap.get(name);
    }

    /**
     * 向工厂中添加指定高校
     * @param name
     * @param manage
     */
    public static void createFactory(String name,IUniversityManage manage){
        universityMap.put(name,manage);
    }

}
