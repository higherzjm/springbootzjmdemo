package com.zjm.springframework.methodInterceptor.interceptor2;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HfiTrace {
}