package com.zjm.customannotation;

import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author zhujianming
 */
@Slf4j
public class MyAnnotationValidation implements ConstraintValidator<MyAnnotation, Integer> {
    private int maxValue;

    @Override
    public void initialize(MyAnnotation myAnnotation) {
        this.maxValue = myAnnotation.MaxValue();
    }


    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        log.info("自定义注解校验中..................");
        return maxValue>value;
    }
}
