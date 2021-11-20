package com.zjm.waggerConfig;

import com.zjm.springframework.Listener_Filter_Initializer_wrapper.CustomHttpRequestFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2  //开启Swagger  返回地址 http://localhost:8090/swagger-ui.html
public class SwaggerConfig {
    //实例化自定义过滤器
    @Bean
    public FilterRegistrationBean Filters() {

        FilterRegistrationBean registrationBean = new FilterRegistrationBean();

        registrationBean.setFilter(new CustomHttpRequestFilter());

        registrationBean.addUrlPatterns("/baseapplication/treeOperation/*");

        registrationBean.setName("customFilter");

        return registrationBean;

    }
}