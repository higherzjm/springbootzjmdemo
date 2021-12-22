package com.zjm.kindsmodels.customermodels3;

import com.zjm.kindsmodels.customermodels1.AbstractUniversity;
import com.zjm.kindsmodels.customermodels1.FdUniversity1;
import com.zjm.kindsmodels.customermodels1.XdUniversity1;
import com.zjm.util.SpringBeanUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhujianming
 * 模板方法模式
 */
@RequestMapping("/kindsmodels3")
@RestController
@Slf4j
@Api(tags = "设计模式")
public class MyController3 {

    @Autowired
    private UniversityContext context;

    /**
     *
     */
    @PostMapping("/universityAddress/{universityName}")
    @ApiOperation(value = "查询高校地址【策略模式】", notes = "query student list")
    public String universityAddress(@ApiParam(name = "universityName", value = "厦门大学、厦门大学", defaultValue = "厦门大学") @PathVariable("universityName") String universityName) {
        String beanName = null;
        if (universityName.equals("厦门大学")) {
            beanName = "xd";
        } else {
            beanName = "fd";
        }
        return context.getContext(beanName).getAddress(universityName);
    }
}
