package com.zjm.my_httpretry;

/**
 * @author zhujianming
 * @description  http 请求超时重试机制
 * @date 2022/6/8 14:01
 */
public class TestMain {
    public static void main(String[] args) {
        ThirdCallService thirdCallService = new ThirdCallServiceImpl();
        ThirdCallService thirdCallServiceProxy = MyRetryFactory.getRetryServiceProxy(thirdCallService);
        thirdCallServiceProxy.push();
    }
}
