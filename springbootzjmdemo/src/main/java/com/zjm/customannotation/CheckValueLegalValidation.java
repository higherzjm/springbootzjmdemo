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
public class CheckValueLegalValidation implements ConstraintValidator<CheckValueLegal, Integer> {
    private int maxValue;
    private CheckValueLegal checkValueLegal;

    @Override
    public void initialize(CheckValueLegal checkValueLegal) {
        this.maxValue = checkValueLegal.MaxValue();
        this.checkValueLegal=checkValueLegal;

    }


    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        log.info("CheckValueLegal自定义注解校验中..................");
        String checkMsg="";
        if (value>maxValue){
            checkMsg="格式有误，设置的最大年龄为"+maxValue+"，你输入的为"+value;
            log.info(checkMsg);
        }
     /*
       TODO 怎么修改动态message提示内容
       try {
            CheckValueLegal checkValueLegal=MyAnnotationVO.class.getDeclaredField("age").getAnnotation(CheckValueLegal.class);
            //获取 foo 这个代理实例所持有的 InvocationHandler
            InvocationHandler invocationHandler = Proxy.getInvocationHandler(checkValueLegal);
            // 获取 AnnotationInvocationHandler 的 memberValues 字段
            Field hField = invocationHandler.getClass().getDeclaredField("memberValues");
            // 因为这个字段事 private final 修饰，所以要打开权限
            hField.setAccessible(true);
            // 获取 memberValues
            Map memberValues = (Map) hField.get(invocationHandler);
            // 修改 value 属性值
            memberValues.put("message", checkMsg);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        return maxValue>=value;
    }
}
