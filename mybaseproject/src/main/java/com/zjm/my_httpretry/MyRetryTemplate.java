package com.zjm.my_httpretry;

import java.util.Arrays;

public abstract class MyRetryTemplate {

    private int retryTimes = 0;

    private int[] retrySeconds = {};

    public abstract Object retry() throws Exception;

    public Object executeOnce() throws Exception {
        System.out.println("第一次执行...");
        return retry();
    }

    public Object execute() {
        System.out.println("重试" + retryTimes + "次-分别相隔" + Arrays.toString(retrySeconds) + "秒");
        for (int i = 0; i < retryTimes; i++) {
            try {
                System.out.println(retrySeconds[i] + "s后准备第[" + (i + 1) + "]次重试！");
                Thread.sleep(1000 * retrySeconds[i]);
                return retry();
            } catch (Exception e) {
                System.out.println("重试失败:" + e);
            }
        }
        return null;
    }

    public void executeAsync() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                execute();
            }
        });
        thread.start();
    }

    public MyRetryTemplate setRetryTimes(int retryTimes) {
        this.retryTimes = retryTimes;
        return this;
    }

    public MyRetryTemplate setRetrySeconds(int[] retrySeconds) {
        this.retrySeconds = retrySeconds;
        return this;
    }
}
