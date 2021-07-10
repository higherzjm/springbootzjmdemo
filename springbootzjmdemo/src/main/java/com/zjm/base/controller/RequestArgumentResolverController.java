package com.zjm.base.controller;

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
@Api(tags = "springBoot�����������")
public class RequestArgumentResolverController {
    /**
     * model��Map ����������Ͷ����������������
     */
    @GetMapping("/modelAndMapRequestArgumentResolver")
    @ApiOperation(value = "model��Map�����������", notes = "model��Map�����������")
    public String modelAndMapRequestArgumentResolver(Map<String, Object> map, Model model, HttpServletRequest request, HttpServletResponse response) {
        map.put("name", "����");
        model.addAttribute("age", 10);
        request.setAttribute("school", "����ʦ����ѧ");
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
         * model��Map ����������Ͷ���������������棬����ֱ����request��ȡ�������
         */
        map.put("name", request.getAttribute("name"));
        map.put("age", request.getAttribute("age"));
        map.put("school", request.getAttribute("school"));
        return map;
    }
}
