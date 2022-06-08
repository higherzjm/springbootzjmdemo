package com.zjm.my_httpretry;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MyRetryFactory {

    public static <T> T getRetryServiceProxy(T realObj) {
        Class<?>[] realIntfs = realObj.getClass().getInterfaces();
        Object proxyInstance = Proxy.newProxyInstance(MyRetryFactory.class.getClassLoader(), realIntfs,
                new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                // 接口方法上是否有@MyRetry
                if (method.isAnnotationPresent(MyRetry.class)) {
                    MyRetry myRetry = method.getDeclaredAnnotation(MyRetry.class);
                    int retryTimes = myRetry.retryTimes();
                    int[] retrySeconds = myRetry.retrySecond();

                    MyRetryTemplate myRetryTemplate = new MyRetryTemplate() {
                        @Override
                        public Object retry() throws Exception {
                            Object obj = method.invoke(realObj, args);
                            if (obj instanceof ResponseResult) {
                                // 网络异常，第三方接口也会返回结果，判断code是否等于0，决定是否重试
                                ResponseResult responseResult = (ResponseResult) obj;
                                if (responseResult == null || 	 (!"0".equals(responseResult.getCode()))) {
                                    if (responseResult == null) {
                                        throw new RuntimeException("接口返回对象为空");
                                    } else {
                                        throw new RuntimeException(responseResult.getMsg());
                                    }
                                }
                            }
                            return obj;
                        }
                    }.setRetryTimes(retryTimes).setRetrySeconds(retrySeconds);

                    // 先执行方法一次，再异步重试
                    try {
                        return myRetryTemplate.executeOnce();
                    } catch(Exception e) {
                        myRetryTemplate.executeAsync();
                    }
                    return null;
                } else {
                    return method.invoke(realObj, args);
                }
            };

        });

        return (T) proxyInstance;
    }
}
