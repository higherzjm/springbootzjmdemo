package com.zjm.redis.requestLimit;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 令牌算法实现限流
 * 思路：初始化令牌数量，然后用定时器指定时间往容器中添加令牌，
 * 获取令牌时判断【blockingQueue.poll】是否还存在令牌，存在则获取成功，否则获取失败，获取成功令牌数量【blockingQueue.size()】自动减1。
 */
@Slf4j
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
     *
     * @return
     */
    public boolean tryAcquire() {
       log.info("当前剩余的令牌数量:"+blockingQueue.size());
       String poll=blockingQueue.poll();
       if (poll==null){
           return false;
       }else {
           log.info("取出还剩余令牌数量:"+blockingQueue.size());
           return true;
       }
    }

    private void addToken() {
        blockingQueue.offer("1");
        log.info("添加令牌成功:"+System.currentTimeMillis()/1000);
    }

    /**
     * 开启定时线程，添加令牌
     */
    private void start() {
        //总体延迟10秒执行，每5[period=5]秒执行一次
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(() -> {
            addToken();
        }, 10, period, timeunit);
    }

    /**
     * 获取令牌
     */
    private void getToken(){
        //总体延迟10秒执行，每5[period=5]秒执行一次
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(() -> {
            //获取令牌
            Boolean isExist=tryAcquire();
            log.info("获取令牌状态:"+isExist);
        }, 1, 1, timeunit);
    }

    public static void main(String[] args) {
        TokenLimiter limiter = new TokenLimiter(3, 5, TimeUnit.SECONDS);
        //开启定时线程，添加令牌
        limiter.start();
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        limiter.getToken();
    }
}