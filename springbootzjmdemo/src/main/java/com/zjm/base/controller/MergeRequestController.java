package com.zjm.base.controller;

import com.zjm.base.service.IMergeRequestService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author zhujianming
 * 合并请求，请求收集
 */
@RequestMapping("/mergeRequest")
@RestController
@Slf4j
@Api(tags = "合并请求，请求收集")
public class MergeRequestController {
    @Autowired
    private IMergeRequestService mergeRequestService;

    //积攒请求
    @RequestMapping("/mergeRequest")
    public Long mergeRequest() {
        return mergeRequestService.mergeRequest();
    }


}

