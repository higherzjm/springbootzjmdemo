package com.zjm.waggerConfig;

import com.zjm.springframework.HandlerInterceptor.MyHandlerInterceptor;
import com.zjm.springframework.HandlerInterceptor.MyHandlerInterceptorAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyInterceptorsConfigurer implements WebMvcConfigurer {
    //添加拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(myInterceptor()).addPathPatterns("/baseapplication/springBootBase/**");
        registry.addInterceptor(new MyHandlerInterceptor()).addPathPatterns("/baseapplication/encryptionAndDecryption/**");
    }

    @Bean
    public MyHandlerInterceptorAdapter myInterceptor() {
        return new MyHandlerInterceptorAdapter();
    }
}