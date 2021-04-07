package com.zjm.base.controller;

import com.zjm.base.VO.SalaryRecheckMergeRequestVO;
import com.zjm.base.service.MyService;
import com.zjm.base.RequestVO;
import com.zjm.base.StudentInfo;
import com.zjm.base.util.JsonUtil;
import com.zjm.base.util.SalaryRecheckMergeRequestUtil;
import io.swagger.annotations.Api;
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
@Api(tags = "springBoot 基础应用")
public class SpringBootBaseController {
    @Autowired
    private MyService myService;

    //post请求，实体请求参数
    @PostMapping("/test1")
    public StudentInfo test1(@RequestBody @Validated RequestVO requestVO) {
        CompletableFuture<String> completedFuture = new CompletableFuture();
        SalaryRecheckMergeRequestUtil.salaryRecheckRequestQueue.add(SalaryRecheckMergeRequestVO.builder().requestPath("springBootBase/test1").
                requestName("post请求，实体请求参数").paramJsonStr(JsonUtil.convertBeanToJson(requestVO)).completedFuture(completedFuture).build());
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

    //http://localhost:8081/myboot/test2/张三/30  request请求，简单一个或多参数
    @RequestMapping("/test2/{name}/{age}")
    public StudentInfo test2(
            @ApiParam(name = "name", value = "姓名", required = true) @PathVariable("name") String name,
            @ApiParam(name = "age", value = "年龄", required = true) @PathVariable("age") Integer age) {
        CompletableFuture<String> completedFuture = new CompletableFuture();
        SalaryRecheckMergeRequestUtil.salaryRecheckRequestQueue.add(SalaryRecheckMergeRequestVO.builder().requestPath("springBootBase/test2").
                requestName("request请求，简单一个或多参数").completedFuture(completedFuture).build());
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

    //http://localhost:8081/myboot/test3?name='张三'
    @RequestMapping("/test3")
    public String test3(@RequestParam("name") String name) {
        return "test3:" + name;
    }

    //http://localhost:8081/myboot/test4/天安门
    @RequestMapping("/test4")
    public String test4(@RequestParam("name") String name) {
        return name;
    }

    //http://localhost:8081/myboot/test5
    @RequestMapping("/test5")
    public String test5() {
        return "无参数";
    }

    //http://localhost:8081/myboot/test4 请求转发
    @RequestMapping("/requestForward")
    public String test5(HttpServletRequest request) {
        String msg = (String) request.getAttribute("msg");
        return msg;
    }

}

