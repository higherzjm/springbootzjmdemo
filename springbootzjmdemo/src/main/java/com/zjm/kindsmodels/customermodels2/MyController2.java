package com.zjm.kindsmodels.customermodels2;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/kindsmodels2")
@RestController
@Slf4j
@Api(tags = "设计模式")
public class MyController2 {
    @PostMapping("/universityAddress/{universityName}")
    @ApiOperation(value = "查询高校地址【策略模式+工厂模式】", notes = "query student list")
    public String universityAddress(@ApiParam(name = "universityName", value = "厦门大学、农林大学、厦门大学",defaultValue = "厦门大学") @PathVariable("universityName") String universityName) {
        IUniversityManage universityManage=UniversityFactory.getFactory(universityName);
        return universityManage.getAddress(universityName);
    }
}
