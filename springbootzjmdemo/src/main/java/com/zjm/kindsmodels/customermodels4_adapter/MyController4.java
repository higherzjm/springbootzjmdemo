package com.zjm.kindsmodels.customermodels4_adapter;

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
 * 适配器模式
 */
@RequestMapping("/kindsmodels4")
@RestController
@Slf4j
@Api(tags = "设计模式")
public class MyController4 {

    @Autowired
    private XdUniversity4 xd;

    /**
     *
     */
    @PostMapping("/universityAddress/{universityName}")
    @ApiOperation(value = "查询高校地址【适配器模式】", notes = "query student list")
    public String universityAddress(@ApiParam(name = "universityName", value = "厦门大学、厦门大学", defaultValue = "厦门大学") @PathVariable("universityName") String universityName) {
        IUniversityManage manage=HandlerUtil.getHandler(xd);
        return manage.getAddress(universityName);
    }
}
