package com.zjm.kindsmodels.Adapter.example2;

/**
 * 适配接口
 */
public interface HandlerAdapter {
    /**
     * 是否匹配
     * @param handler
     * @return
     */
    public boolean supports(Object handler);
    /**
     * 处理适配的方法
     * @param handler
     * @return
     */
    public void handle(Object handler);  
}