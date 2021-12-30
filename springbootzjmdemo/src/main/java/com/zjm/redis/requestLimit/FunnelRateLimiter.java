package com.zjm.redis.requestLimit;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 漏斗算法实现限流
 * 思路:设置每毫秒从漏斗流出的请求数量，实例化对象是获取当前毫秒数，
 * 流入请求时再获取当前毫秒数(容量加)，计算当前时间段流出的数量(容量减)，得出剩余的容量总数
 */
public class FunnelRateLimiter {
    static class Funnel {
        //容量
        int capacity;
        //流出率，指定毫秒数流出的数量
        BigDecimal leakingRate;
        //剩余数量
        int leftQuota;
        long leakingTs;

        public Funnel(int capacity, BigDecimal leakingRate) {
            this.capacity = capacity;
            this.leakingRate = leakingRate;
            this.leftQuota = capacity;
            this.leakingTs = System.currentTimeMillis();
        }

        void makeSpace() {
            long nowTs = System.currentTimeMillis();
            long deltaTs = nowTs - leakingTs;
            int deltaQuota = new BigDecimal(deltaTs).multiply(leakingRate).intValue();//流出的数量
            System.out.println(deltaTs+"*"+leakingRate+"流出数量:"+deltaQuota);
            // 间隔时间太长，整数数字过大溢出
            if (deltaQuota < 0) {
                this.leftQuota = capacity;
                this.leakingTs = nowTs;
                return;
            }
            // 腾出空间太小，最小单位是 1
            if (deltaQuota < 1) {
                return;
            }
            this.leftQuota += deltaQuota;
            this.leakingTs = nowTs;
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
        FunnelRateLimiter funne = new FunnelRateLimiter();
        BigDecimal leakingRate= new BigDecimal("1").divide(new BigDecimal("2000"));//每二千毫秒流出1个
        for (int i=0;i<30;i++) {
            Thread.sleep(1000);
            Boolean b = funne.isActionAllowed("aaa", "bb", 10, leakingRate);
            System.out.println(i+"--" + b);
        }
    }
}
