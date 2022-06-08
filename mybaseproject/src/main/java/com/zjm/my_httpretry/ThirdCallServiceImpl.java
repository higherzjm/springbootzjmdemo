package com.zjm.my_httpretry;

/**
 * @author zhujianming
 * @description
 * @date 2022/6/8 14:00
 */
public class ThirdCallServiceImpl implements ThirdCallService {
    @Override
    public ResponseResult push() {
        System.out.println("push()");
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode("-1");
        responseResult.setMsg("连接超时，网络异常");
        return responseResult;
    }
}
