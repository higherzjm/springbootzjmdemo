package com.zjm.customannotation;

import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * @author zhujianming
 */
@Slf4j
public class CheckIsNotEmptyValidation implements ConstraintValidator<CheckIsNotEmpty, Object> {

    @Override
    public void initialize(CheckIsNotEmpty checkIsNotEmpty) {
    }


    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        log.info("CheckIsNotEmpty自定义注解校验中..................");

        return value!=null&&!"".equals(value);
    }
}
