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
public class TokenUtil {
    public static ArrayBlockingQueue<String> blockingQueue;
    public static int limit;

    /**
     * 初始化令牌
     */
    public static void init() {
        blockingQueue = new ArrayBlockingQueue<>(limit);
        for (int i = 0; i < limit; i++) {
            blockingQueue.add("1");
        }
        //启动线程，指定时间往容器中添加令牌
        start();
    }

    /**
     * 开启定时线程，添加令牌
     */
    public static void start() {
        //总体延迟10秒执行，每5秒执行一次
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(() -> {
                    addToken();
                },
                10, 5, TimeUnit.SECONDS);
    }

    /**
     * 添加令牌
     */
    public static void addToken() {
        blockingQueue.offer("1");
        log.info("添加令牌成功,令牌桶大小:" + blockingQueue.size());
    }

    /**
     * 获取令牌，如果为空返回FALSE
     *
     * @return
     */
    public static boolean tryGetToken() {
        log.info("当前剩余的令牌数量:" + blockingQueue.size());
        String poll = blockingQueue.poll();
        if (poll == null) {
            return false;
        } else {
            log.info("取出还剩余令牌数量:" + blockingQueue.size());
            return true;
        }
    }

}