package com.zjm.springframework.singleton_prototype;

import com.zjm.baseapplication.VO.Student;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zhujianming
 * @description 单例的bean如何注入多例的service
 * 【@RestController】 注解的bean是单例，它注入的service也会为单例
 * 单例：每次请求获取的bean一样【bean内存地址一样】，不会每次请求实例化一次，系统启动时默认初始化一次就可以了
 * 多列：每次请求获取的bean不一样【bean内存地址不一样】,每次请求都会实例化一次bean
 *
 * @date 2022/3/19 14:05
 */
@RequestMapping("/singletonPrototypeTest")
@RestController
@Slf4j
@Api(tags = "单双例测试")
public class SayController {
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    List<AbstractService> sayServiceList;
    @Autowired
    SayHello hello;

    @PostMapping("/test1")
    @ApiOperation(value = "测试1-常规注入抽象类")
    public void test1() {
        sayServiceList.forEach(AbstractService::say);
    }

    /**
     * @description 【applicationContext】上下文获取bean service生命周期或按service定义的生命周期类型获取
     * @date 2022/3/19 21:56
     */
    @PostMapping("/test2")
    @ApiOperation(value = "测试2-ApplicationContext注入抽象类")
    public void test2() {
        log.info("test2请求时间:{}",System.currentTimeMillis()/1000);
        applicationContext.getBeansOfType(AbstractService.class).values().forEach(AbstractService::say);

    }

    /**
     * @description  通过【autowired】注入方式获取的到的bean会沿用control的生命周期
     * @date 2022/3/19 21:58
     */
    @PostMapping("/test3")
    @ApiOperation(value = "测试1-常规注入子类")
    public Student test3(@RequestBody @Validated Student student) {
        hello.say();
        return student;
    }
    @GetMapping("/test4/{paramValue}")
    @ApiOperation(value = "测试1-ApplicationContext注入子类")
    public String test4(@PathVariable String paramValue) {
        log.info("test4请求时间:{}",System.currentTimeMillis()/1000);
        applicationContext.getBeansOfType(SayHello.class).values().forEach(SayHello::say);
        return "ret:"+paramValue;
    }
}
