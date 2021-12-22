package com.zjm.kindsmodels.customermodels3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ��У������
 */
@Service
public class UniversityContext {

    private final Map<String, IUniversityManage> strategyMap = new ConcurrentHashMap<>();

    /**
     * ���캯��������ע��
     */
    public UniversityContext(Map<String, IUniversityManage> strategyMap) {
        strategyMap.forEach(this.strategyMap::put);
    }
 
    public IUniversityManage getContext(String beanName){
        return strategyMap.get(beanName)==null?null:strategyMap.get(beanName);
    }
 
}