package com.zjm.base.controller;

import com.zjm.base.service.MyService;
import com.zjm.base.RequestVO;
import com.zjm.base.StudentInfo;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhujianming
 */
@RequestMapping("/myboot")
@RestController
public class MyController {
    @Autowired
    private MyService myService;

    @PostMapping("/test1")
    public StudentInfo test1(@RequestBody @Validated RequestVO requestVO) {
        return  myService.getStudentInfo1(requestVO);
    }
    //http://localhost:8081/myboot/test2/张三/30
    @RequestMapping("/test2/{name}/{age}")
    public StudentInfo test2(
            @ApiParam(name = "name", value = "姓名", required = true) @PathVariable("name") String name,
            @ApiParam(name = "age", value = "年龄", required = true) @PathVariable("age") Integer age) {
        return  myService.getStudentInfo2(name, age);
    }
    //http://localhost:8081/myboot/test3/张三   带参数的请求不能过滤
    @RequestMapping("/test3/{name}")
    public String test3(@PathVariable("name") String name) {
        return  "test3:"+name;
    }
    @RequestMapping("/test4")
    public String test4() {
        return  "test4";
    }

}

