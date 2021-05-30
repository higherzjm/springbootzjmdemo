package com.zjm.base.Interceptors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {
    //添加拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(myInterceptor()).addPathPatterns("/base/springBootBase/**");
        registry.addInterceptor(new MyInterceptorHandlerInterceptor()).addPathPatterns("/base/encryptionAndDecryption/**");
    }

    @Bean
    public MyInterceptorHandlerInterceptorAdapter myInterceptor() {
        return new MyInterceptorHandlerInterceptorAdapter();
    }
}