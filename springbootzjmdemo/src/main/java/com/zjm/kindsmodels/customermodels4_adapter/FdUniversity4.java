package com.zjm.kindsmodels.customermodels4_adapter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

/**
 * 福大
 */
@Slf4j
@Service
public class FdUniversity4 implements IUniversityManage, InitializingBean {

    @Override
    public String getAddress(String name) {
        // 处理其他业务......
        log.info("处理福州大学业务");
        return name+"地址:福州市静安区";
    }

    @Override
    public boolean supports(IUniversityManage manage) {
        return (manage instanceof FdUniversity4);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        HandlerUtil.handlerAdapters.add(this);
    }
}
