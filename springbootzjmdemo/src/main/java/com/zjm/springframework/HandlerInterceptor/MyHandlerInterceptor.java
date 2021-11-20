package com.zjm.springframework.HandlerInterceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description: 拦截器   实现HandlerInterceptor接口或继承HandlerInterceptorAdapter抽象类都可以
 * @Author: zhujianming
 * @Date: 2021/5/30
 *
 **/
@Slf4j
public class MyHandlerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("拦截器预选处理->preHandle");
        String path = request.getServletPath();
        log.info("path:" + path);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("请求发送->postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("请求结束->afterCompletion");
    }
}

