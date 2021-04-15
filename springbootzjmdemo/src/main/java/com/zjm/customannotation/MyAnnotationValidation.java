package com.zjm.customannotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author zhujianming
 */
public class MyAnnotationValidation implements ConstraintValidator<MyAnnotation, Integer> {
    private int maxValue;

    @Override
    public void initialize(MyAnnotation myAnnotation) {
        this.maxValue = myAnnotation.MaxValue();
    }


    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return maxValue>value;
    }
}
