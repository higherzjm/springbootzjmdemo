package com.zjm.baseapplication.controller;

import com.zjm.baseapplication.service.IMergeRequestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author zhujianming
 * 合并请求，请求收集
 */
@RequestMapping("/base/mergeRequest")
@RestController
@Slf4j
@Api(tags = "合并请求，请求收集")
public class MergeRequestController {
    @Autowired
    private IMergeRequestService mergeRequestService;

    //积攒请求
    @GetMapping("/mergeRequest")
    @ApiOperation(value = "积攒请求", notes = "积攒请求")
    public Long mergeRequest() {
        return mergeRequestService.mergeRequest();
    }


}

