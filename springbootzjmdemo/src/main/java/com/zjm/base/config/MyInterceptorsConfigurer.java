package com.zjm.base.config;

import com.zjm.base.Interceptors.MyInterceptor_HandlerInterceptor;
import com.zjm.base.Interceptors.MyInterceptor_HandlerInterceptorAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyInterceptorsConfigurer implements WebMvcConfigurer {
    //添加拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(myInterceptor()).addPathPatterns("/base/springBootBase/**");
        registry.addInterceptor(new MyInterceptor_HandlerInterceptor()).addPathPatterns("/base/encryptionAndDecryption/**");
    }

    @Bean
    public MyInterceptor_HandlerInterceptorAdapter myInterceptor() {
        return new MyInterceptor_HandlerInterceptorAdapter();
    }
}