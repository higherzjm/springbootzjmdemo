package com.zjm.kindsmodels.customermodels3;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 厦大
 */
@Slf4j
@Service(value = "xd")
public class XdUniversity3 implements IUniversityManage {

    @Override
    public String getAddress(String name) {
        // 处理其他业务......
        log.info("处理厦门大学业务");
        return name+"地址:厦门市思明区";
    }
}
