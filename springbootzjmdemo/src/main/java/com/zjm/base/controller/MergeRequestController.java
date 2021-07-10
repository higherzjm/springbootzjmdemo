package com.zjm.base.controller;

import com.zjm.base.service.IMergeRequestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author zhujianming
 * �ϲ����������ռ�
 */
@RequestMapping("/base/mergeRequest")
@RestController
@Slf4j
@Api(tags = "�ϲ����������ռ�")
public class MergeRequestController {
    @Autowired
    private IMergeRequestService mergeRequestService;

    //��������
    @GetMapping("/mergeRequest")
    @ApiOperation(value = "��������", notes = "��������")
    public Long mergeRequest() {
        return mergeRequestService.mergeRequest();
    }


}

