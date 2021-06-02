package com.zjm.base.customException;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
/**
 * 自定义全局异常处理器
 **/
@ControllerAdvice(basePackages ="com.zjm.base.controller")  //如果不指定路径所有control都生效
public class MyControllerAdvice {
 
 
  @ResponseBody
  @ExceptionHandler(value = Exception.class)
  public Map<String,Object> exceptionHandler(Exception ex){
    Map<String,Object> map = new HashMap<String,Object>();
    map.put("code",100);
    map.put("msg","自定义异常:"+ex.getMessage());
    return map;
  }
 
}