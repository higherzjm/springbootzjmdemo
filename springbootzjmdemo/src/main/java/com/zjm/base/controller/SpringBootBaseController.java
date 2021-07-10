package com.zjm.base.controller;

import com.zjm.base.VO.SalaryRecheckMergeRequestVO;
import com.zjm.base.service.MyService;
import com.zjm.base.RequestVO;
import com.zjm.base.StudentInfo;
import com.zjm.base.util.JsonUtil;
import com.zjm.base.util.SalaryRecheckMergeRequestUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author zhujianming
 */
@RequestMapping("/base/springBootBase")
@RestController
@Api(tags = "springBoot ����Ӧ��")
public class SpringBootBaseController {
    @Autowired
    private MyService myService;

    //post����ʵ���������
    @PostMapping("/test1")
    @ApiOperation(value = "post����ʵ���������", notes = "post����ʵ���������")
    public StudentInfo test1(@RequestBody @Validated RequestVO requestVO) {
        CompletableFuture<String> completedFuture = new CompletableFuture();
        SalaryRecheckMergeRequestUtil.salaryRecheckRequestQueue.add(SalaryRecheckMergeRequestVO.builder().requestPath("springBootBase/test1").
                requestName("post����ʵ���������").paramJsonStr(JsonUtil.convertBeanToJson(requestVO)).completedFuture(completedFuture).build());
        try {
            String mergeRequestMsg = completedFuture.get();
            requestVO.setName(mergeRequestMsg);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return myService.getStudentInfo1(requestVO);
    }

    @GetMapping("/test2/{name}/{age}")
    @ApiOperation(value = "PathVariable��������", notes = "PathVariable��������")
    public StudentInfo test2(
            @ApiParam(name = "name", value = "����", required = true) @PathVariable("name") String name,
            @ApiParam(name = "age", value = "����", required = true) @PathVariable("age") Integer age) {
        CompletableFuture<String> completedFuture = new CompletableFuture();
        SalaryRecheckMergeRequestUtil.salaryRecheckRequestQueue.add(SalaryRecheckMergeRequestVO.builder().requestPath("springBootBase/test2").
                requestName("request���󣬼�һ��������").completedFuture(completedFuture).build());
        try {
            String mergeRequestMsg = completedFuture.get();
            name = mergeRequestMsg;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return myService.getStudentInfo2(name, age);
    }

    @GetMapping("/test3")
    @ApiOperation(value = "RequestParam��������", notes = "RequestParam��������")
    public String test3(@RequestParam("name") String name) {
        return "test3:" + name;
    }

    @GetMapping("/test4/{name}")
    @ApiOperation(value = "����ת��", notes = "����ת��")
    public String test4(@PathVariable("name") String name) {
        return name;
    }

    @GetMapping("/requestForward")
    @ApiOperation(value = "��������ת��(�����Ч)", notes = "��������ת��(�����Ч)")
    public String test5(HttpServletRequest request) {
        String msg = (String) request.getAttribute("msg");
        return "����ת��:"+msg;
    }

}

