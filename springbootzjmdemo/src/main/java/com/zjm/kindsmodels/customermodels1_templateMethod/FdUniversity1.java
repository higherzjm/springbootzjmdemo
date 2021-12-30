package com.zjm.kindsmodels.customermodels1_templateMethod;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 福大
 */
@Slf4j
@Service
public class FdUniversity1 extends AbstractUniversity {

    @Override
    public String getAddress(String name) {
        // 处理其他业务......
        log.info("处理福州大学业务");
        return name+"地址:福州市静安区";
    }
}
