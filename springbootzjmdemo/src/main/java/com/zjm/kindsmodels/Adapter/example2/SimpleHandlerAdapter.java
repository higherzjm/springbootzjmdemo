package com.zjm.kindsmodels.Adapter.example2;

public class SimpleHandlerAdapter implements HandlerAdapter {
  
  
    public void handle(Object handler) {  
        ((SimpleController)handler).doSimplerHandler();  
    }  
  
    public boolean supports(Object handler) {  
        return (handler instanceof SimpleController);  
    }  
  
} 