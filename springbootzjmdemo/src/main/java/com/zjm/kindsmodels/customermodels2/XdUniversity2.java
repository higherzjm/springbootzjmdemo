package com.zjm.kindsmodels.customermodels2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

/**
 * 厦大
 */
@Slf4j
@Service
public class XdUniversity2 implements IUniversityManage, InitializingBean {
    @Override
    public String getAddress(String name) {
        // 处理其他业务......
        log.info("处理厦门大学业务");
        return name+"地址:厦门市思明区";
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        UniversityFactory.createFactory("厦门大学",this);
    }
}
