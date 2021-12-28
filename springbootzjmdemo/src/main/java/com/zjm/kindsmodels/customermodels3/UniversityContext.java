package com.zjm.kindsmodels.customermodels3;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 高校上下文
 */
@Service
public class UniversityContext {

    private final Map<String, IUniversityManage> strategyMap = new ConcurrentHashMap<>();

    /**
     * 构造函数按类型注入
     */
    public UniversityContext(Map<String, IUniversityManage> strategyMap) {
        strategyMap.forEach(this.strategyMap::put);
    }
 
    public IUniversityManage getContext(String beanName){
        return strategyMap.get(beanName)==null?null:strategyMap.get(beanName);
    }
 
}