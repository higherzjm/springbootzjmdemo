package com.zjm.customannotation;

import java.lang.annotation.*;

/**
 * 动态查询列标识
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DynamicQueryColumFlag {
    /**
     * 展示顺序
     * @return
     */
    int order() default 0;
}