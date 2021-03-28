package com.zjm.base.config;

import com.zjm.base.filter.CustomHttpRequestFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2  //开启Swagger  返回地址 http://localhost:8081/swagger-ui.html
public class SwaggerConfig {
    @Bean
    public FilterRegistrationBean Filters() {

        FilterRegistrationBean registrationBean = new FilterRegistrationBean();

        registrationBean.setFilter(new CustomHttpRequestFilter());

        registrationBean.addUrlPatterns("/*");

        registrationBean.setName("customFilter");

        return registrationBean;

    }
}