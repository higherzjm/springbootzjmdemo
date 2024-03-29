package com.zjm.baseapplication.controller;

import com.zjm.baseapplication.VO.SalaryRecheckMergeRequestVO;
import com.zjm.baseapplication.service.MyService;
import com.zjm.baseapplication.VO.RequestVO;
import com.zjm.baseapplication.VO.StudentInfo;
import com.zjm.baseapplication.service.impl.MyServiceImpl;
import com.zjm.util.JsonUtil;
import com.zjm.util.MergeRequestUtil;
import com.zjm.util.SpringBeanUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class SpringBootBaseController {
    @Autowired
    private MyService myService;

    //post请求，实体请求参数
    @PostMapping("/test1")
    @ApiOperation(value = "post请求，实体请求参数", notes = "post请求，实体请求参数")
    public StudentInfo test1(@RequestBody @Validated RequestVO requestVO) {
        CompletableFuture<String> completedFuture = new CompletableFuture();
        MergeRequestUtil.salaryRecheckRequestQueue.add(SalaryRecheckMergeRequestVO.builder().requestPath("springBootBase/test1").
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

    @GetMapping("/test2/{name}/{age}")
    @ApiOperation(value = "PathVariable参数请求", notes = "PathVariable参数请求")
    public StudentInfo test2(
            @ApiParam(name = "name", value = "姓名", required = true) @PathVariable("name") String name,
            @ApiParam(name = "age", value = "年龄", required = true) @PathVariable("age") Integer age) {
        CompletableFuture<String> completedFuture = new CompletableFuture();
        MergeRequestUtil.salaryRecheckRequestQueue.add(SalaryRecheckMergeRequestVO.builder().requestPath("springBootBase/test2").
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

    @GetMapping("/test3")
    @ApiOperation(value = "RequestParam参数请求", notes = "RequestParam参数请求")
    public String test3(@RequestParam("name") String name) {
        return "test3:" + name;
    }

    @GetMapping("/test4/{name}")
    @ApiOperation(value = "请求转发", notes = "请求转发")
    public String test4(@PathVariable("name") String name) {
        return name;
    }

    @GetMapping("/requestForward")
    @ApiOperation(value = "接受请求转发(点击无效)", notes = "接受请求转发(点击无效)")
    public String test5(HttpServletRequest request) {
        String msg = (String) request.getAttribute("msg");
        return "请求转发:"+msg;
    }
    @GetMapping("/test")
    @ApiOperation(value = "临时测试", notes = "临时测试")
    public void test() {

        MyService myService= SpringBeanUtils.getBean(MyServiceImpl.class);
        StudentInfo studentInfo=myService.getStudentInfo2("张三",2022);
        log.info("studentInfo:"+ studentInfo);
    }
}

