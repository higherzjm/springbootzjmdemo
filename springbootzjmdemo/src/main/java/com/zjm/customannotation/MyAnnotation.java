package com.zjm.customannotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author zhujianming
 */
@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = MyAnnotationValidation.class)
public @interface MyAnnotation {
    int MaxValue() default 100;

    String message() default "格式有误";

    //下面两句必须加，不然会报but does not contain a groups parameter.
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
