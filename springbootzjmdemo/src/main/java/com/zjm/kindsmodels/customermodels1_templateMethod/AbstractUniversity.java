package com.zjm.kindsmodels.customermodels1_templateMethod;

import org.springframework.stereotype.Service;

@Service
public abstract class AbstractUniversity implements IUniversityManage{
    /**
     * 名称分析
     * @param name 学校名称
     * @return
     */
    @Override
    public String analysisName(String name) {
        //根据具体继承类调用对应的getAddress(..)
        return getAddress(name);
    }

    public abstract String getAddress(String name);
}
