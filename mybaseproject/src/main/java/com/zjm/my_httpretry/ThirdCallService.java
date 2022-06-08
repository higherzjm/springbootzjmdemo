package com.zjm.my_httpretry;

/**
 * @description
 * @Author zhujianming
 * @date 2022/6/8 13:59
 */
public interface ThirdCallService {
    @MyRetry(retryTimes = 2, retrySecond = {3, 5})
    ResponseResult push();
}
