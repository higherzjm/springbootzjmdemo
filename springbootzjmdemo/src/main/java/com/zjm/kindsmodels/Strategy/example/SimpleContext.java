package com.zjm.kindsmodels.Strategy.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SimpleContext {
 
    @Autowired
    private final Map<String, Strategy> strategyMap = new ConcurrentHashMap<>();

    /**
     * 构造函数按类型注入
     */
    public SimpleContext(Map<String, Strategy> strategyMap) {
        this.strategyMap.clear();
        strategyMap.forEach(this.strategyMap::put);
    }
 
    public String getResource(String poolId){
        return strategyMap.get(poolId)==null?"未找到策略,已有策略："+strategyMap:strategyMap.get(poolId).getVpcList(poolId);
    }
 
}