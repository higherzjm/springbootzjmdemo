package com.zjm.base.aop_methodInterceptor.interceptor2;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HfiTrace {
}