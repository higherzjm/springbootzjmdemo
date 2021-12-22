package com.zjm.kindsmodels.customermodels1;

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
@RequestMapping("/kindsmodels1")
@RestController
@Slf4j
@Api(tags = "设计模式")
public class MyController1 {
    @Autowired
    private XdUniversity1 xd;

    @PostMapping("/universityAddress/{universityName}")
    @ApiOperation(value = "查询高校地址【模板方法模式】", notes = "query student list")
    public String universityAddress(@ApiParam(name = "universityName", value = "厦门大学、厦门大学", defaultValue = "厦门大学") @PathVariable("universityName") String universityName) {
        AbstractUniversity university = null;
        if (universityName.equals("厦门大学")) {
            university = SpringBeanUtils.getBean(XdUniversity1.class);
        } else {
            university = SpringBeanUtils.getBean(FdUniversity1.class);
        }
        return university.analysisName(universityName);
    }
}
