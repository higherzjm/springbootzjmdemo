package com.zjm.kindsmodels.customermodels2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

/**
 * 农大
 */
@Slf4j
@Service
public class NlUniversity implements IUniversityManage, InitializingBean {
    @Override
    public String getAddress(String name) {
        // 处理其他业务......
        log.info("处理农林大学业务");
        return  name+"地址:福州市鼓楼区";
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        UniversityFactory.createFactory("农林大学",this);
    }
}
