package com.zjm.baseapplication.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
@Controller
@Api(tags = "springBoot请求参数解析")
public class RequestArgumentResolverController {
    /**
     * model、Map 请求参数类型都会进入请求域里面
     */
    @GetMapping("/modelAndMapRequestArgumentResolver")
    @ApiOperation(value = "model、Map请求参数解析", notes = "model、Map请求参数解析")
    public String modelAndMapRequestArgumentResolver(Map<String, Object> map, Model model, HttpServletRequest request, HttpServletResponse response) {
        map.put("name", "张三");
        model.addAttribute("age", 10);
        request.setAttribute("school", "福建师范大学");
        Cookie cookie = new Cookie("password", "123456");
        cookie.setComment("localhost");
        response.addCookie(cookie);

        return "forward:/forwardRequest";
    }

    @ResponseBody
    @GetMapping("/forwardRequest")
    public Map forwardRequest(HttpServletRequest request) {
        Map map = new HashMap();
        /**
         * model、Map 请求参数类型都会进入请求域里面，可以直接用request获取参数结果
         */
        map.put("name", request.getAttribute("name"));
        map.put("age", request.getAttribute("age"));
        map.put("school", request.getAttribute("school"));
        return map;
    }
}
