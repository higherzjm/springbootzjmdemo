package com.zjm.base.Interceptors;

import com.zjm.base.RequestVO;
import com.zjm.base.service.MyService;
import com.zjm.base.StudentInfo;
import com.zjm.base.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StreamUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

@Slf4j
public class MyInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private MyService myService;

    /**
     * 在请求处理之前进行调用（Controller方法调用之前）
     * 基于URL实现的拦截器
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String path = request.getServletPath();
        System.out.println("path:" + path);
        if (true) {
            if (path.contains("myboot")) {
                int pathSize = path.length();
                //无参数解析
                if (path.contains("test5")) {
                    return true;
                }
                //get 请求，简单一个或多参数解析
                if (path.contains("test2")) {
                    String paramString = path.substring("/myboot/test2/".length(), pathSize);
                    Object[] paramArray = paramString.split("/");
                    String name = paramArray[0].toString();
                    Integer age = Integer.parseInt(paramArray[1].toString());

                    StudentInfo studentInfo = myService.getStudentInfo2(name, age);
                    log.info(studentInfo.toString());
                }
                //post请求，实体请求参数解析
                if (path.contains("test1")) {
                    InputStream inputStream = request.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    char[] charBuffer = new char[128];
                    int bytesRead;
                    StringBuffer stringBuilder = new StringBuffer();
                    while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                        stringBuilder.append(charBuffer, 0, bytesRead);
                    }
                    log.info("请求stringBuilder:" + stringBuilder.toString());
                    RequestVO requestVO = JsonUtil.convertJsonToBean(stringBuilder.toString(), RequestVO.class);
                    log.info("请求VO:" + requestVO);
                }
                //请求转发
                if (path.contains("test4")) {
                    String name = path.substring(path.lastIndexOf("/") + 1, pathSize);
                    request.setAttribute("msg", "请求转发:" + name);
                    request.getRequestDispatcher("/myboot/requestForward").forward(request, response);
                    return false;
                }
            }
            return true;
        } else {
            // 这写你拦截需要干的事儿，比如取缓存，SESSION，权限判断等  
            System.out.println("====================================");
            return true;
        }
    }
} 