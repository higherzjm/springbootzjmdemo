package com.zjm.base.Interceptors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfigurer implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 拦截所有请求，通过判断是否有 @LoginRequired 注解 决定是否需要登录  
        registry.addInterceptor(myInterceptor()).addPathPatterns("/redis/*");
        registry.addInterceptor(myInterceptor()).addPathPatterns("/myboot/*");
        registry.addInterceptor(myInterceptor()).addPathPatterns("/myboot2/*");
    }

    @Bean
    public MyInterceptor myInterceptor() {
        return new MyInterceptor();
    }
}