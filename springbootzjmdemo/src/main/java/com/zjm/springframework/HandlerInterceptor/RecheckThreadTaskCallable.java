package com.zjm.springframework.HandlerInterceptor;

import java.util.concurrent.Callable;

//复核线程任务调度
public class RecheckThreadTaskCallable implements Callable<String> {

    private String retMsg;

    public RecheckThreadTaskCallable(String retMsg) {
        this.retMsg = retMsg;
    }

    @Override
    public String call() throws Exception {
        return retMsg;
    }
}