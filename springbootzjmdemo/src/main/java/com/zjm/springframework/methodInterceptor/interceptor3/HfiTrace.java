package com.zjm.springframework.methodInterceptor.interceptor3;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HfiTrace {
}