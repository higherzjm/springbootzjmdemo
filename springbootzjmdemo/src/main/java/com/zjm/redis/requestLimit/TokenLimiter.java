package com.zjm.redis.requestLimit;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TokenLimiter {
    private ArrayBlockingQueue<String> blockingQueue;
    private int limit;
    private TimeUnit timeunit;
    private int period;

    public TokenLimiter(int limit, int period, TimeUnit timeUnit) {
        this.limit = limit;
        this.timeunit = timeUnit;
        this.period = period;
        blockingQueue = new ArrayBlockingQueue<>(limit);
        init();
        start();
    }

    /**
     * 初始化令牌
     */
    private void init() {
        for (int i = 0; i < limit; i++) {
            blockingQueue.add("1");
        }
    }

    /**
     * 获取令牌，如果为空返回FALSE
     * @return
     */
    public boolean tryAcquire() {
        return blockingQueue.poll() == null ? false : true;
    }

    private void addToken() {
        blockingQueue.offer("1");
    }

    /**
     * 开启定时线程，添加令牌
     */
    private void start() {
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(() -> {
            addToken();
        }, 10, period, timeunit);
    }
}