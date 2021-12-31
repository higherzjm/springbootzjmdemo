package com.zjm.redis.requestLimit;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 漏斗算法实现限流
 * 思路:设置每毫秒从漏斗流出的请求数量，实例化对象是获取当前毫秒数，
 * 流入请求时再获取当前毫秒数(容量加)，计算当前时间段流出的数量(容量减)，得出剩余的容量总数
 */
@Slf4j
public class FunnelRateLimiter {
    private static long leakingTs = System.currentTimeMillis();

    static class Funnel {
        //容量
        int capacity;
        //流出率，指定毫秒数流出的数量
        BigDecimal leakingRate;
        //剩余数量
        int leftQuota;


        public Funnel(int capacity, BigDecimal leakingRate) {
            this.capacity = capacity;
            this.leakingRate = leakingRate;
            this.leftQuota = capacity;

        }

        void makeSpace() {
            long nowTs = System.currentTimeMillis();
            long deltaTs = nowTs - leakingTs;
            int deltaQuota = new BigDecimal(deltaTs).multiply(leakingRate).intValue();//流出的数量
            log.info(deltaTs + "*" + leakingRate + "流出数量:" + deltaQuota);
            // 间隔时间太长，整数数字过大溢出
            if (deltaQuota < 0) {
                this.leftQuota = capacity;
                leakingTs = nowTs;
                return;
            }
            // 腾出空间太小，最小单位是 1
            if (deltaQuota < 1) {
                return;
            }
            this.leftQuota += deltaQuota;
            leakingTs = nowTs;
            if (this.leftQuota > this.capacity) {
                this.leftQuota = this.capacity;
            }
        }

        boolean watering(int quota) {
            makeSpace();
            if (this.leftQuota >= quota) {
                this.leftQuota -= quota;
                return true;
            }
            return false;
        }
    }

    private Map<String, Funnel> funnels = new HashMap<>();

    public boolean isActionAllowed(String userId, String actionKey, int capacity, BigDecimal leakingRate) {
        String key = String.format("%s:%s", userId, actionKey);
        Funnel funnel = funnels.get(key);
        if (funnel == null) {
            funnel = new Funnel(capacity, leakingRate);
            funnels.put(key, funnel);
        }
        return funnel.watering(1);
    }

    public static void main(String[] args) throws InterruptedException {
        FunnelRateLimiter rateLimiter = new FunnelRateLimiter();
        rateLimiter.start();
    }
    private void start() {
        //每一千毫秒流出2000个
        BigDecimal leakingRate = new BigDecimal("5000").divide(new BigDecimal("1000"));

        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(() -> {
            boolean b = isActionAllowed("vvTest", UUID.randomUUID().toString(), 10, leakingRate);
            log.info("--" + b);
        }, 1, 2, TimeUnit.MILLISECONDS);
    }

}
