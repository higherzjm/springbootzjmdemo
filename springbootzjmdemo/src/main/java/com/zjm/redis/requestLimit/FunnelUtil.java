package com.zjm.redis.requestLimit;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

@Slf4j
public class FunnelUtil {
    //容量
    static int capacity;
    //流出率，指定毫秒数流出的数量
    static BigDecimal leakingRate;
    //剩余数量
    static int leftQuota;
    static long leakingTs=System.currentTimeMillis();;

    /**
     * 匹配空间
     */
    private static void makeSpace() {
        long nowTs = System.currentTimeMillis();
        long deltaTs = nowTs - leakingTs;
        deltaTs = deltaTs / 1000;
        int deltaQuota = new BigDecimal(deltaTs).multiply(leakingRate).intValue();//流出的数量
        log.info(deltaTs + "*" + leakingRate + "流出数量:" + deltaQuota);
        // 间隔时间太长，整数数字过大溢出
        if (deltaQuota < 0) {
            leftQuota = capacity;
            leakingTs = nowTs;
            return;
        }
        // 腾出空间太小，最小单位是 1
        if (deltaQuota < 1) {
            return;
        }
        leftQuota += deltaQuota;
        leakingTs = nowTs;
        if (leftQuota > capacity) {
            leftQuota = capacity;
        }
    }

    public static boolean watering(int quota) {
        makeSpace();
        if (leftQuota >= quota) {
            leftQuota -= quota;
            return true;
        }
        return false;
    }
}