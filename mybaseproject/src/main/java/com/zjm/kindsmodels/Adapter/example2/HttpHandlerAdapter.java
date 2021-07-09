package com.zjm.kindsmodels.Adapter.example2;

public class HttpHandlerAdapter implements HandlerAdapter {
  
    public void handle(Object handler) {  
        ((HttpController)handler).doHttpHandler();  
    }  
  
    public boolean supports(Object handler) {  
        return (handler instanceof HttpController);  
    }  
  
}  