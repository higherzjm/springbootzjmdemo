package com.zjm.base.Interceptors;

import com.zjm.base.service.MyService;
import com.zjm.base.StudentInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Slf4j
public class MyInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private MyService myService;
    /**  
     * 在请求处理之前进行调用（Controller方法调用之前）  
     * 基于URL实现的拦截器  
     * @param request  
     * @param response  
     * @param handler  
     * @return  
     * @throws Exception  
     */  
    @Override  
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String path = request.getServletPath();
        System.out.println("path:"+path);
        if (true) {
            if (path.contains("myboot")) {
                StudentInfo studentInfo = myService.getStudentInfo2(request.getParameter("name"),
                        Integer.parseInt(request.getParameter("age")==null?"100":request.getParameter("age")));
                log.info(studentInfo.toString());
            }
            return true;
        } else {  
            // 这写你拦截需要干的事儿，比如取缓存，SESSION，权限判断等  
            System.out.println("====================================");  
            return true;  
        }  
    }  
} 