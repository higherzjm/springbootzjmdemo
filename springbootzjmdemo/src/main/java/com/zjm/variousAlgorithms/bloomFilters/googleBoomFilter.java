package com.zjm.variousAlgorithms.bloomFilters;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * google布隆过滤器
 * redis创建布隆过滤器有3个参数，key，error_rate， initial_size，错误率越低，
 * 需要的空间越大，error_rate表示预计错误率，initial_size参数表示预计放入的元素数量，
 *
 */
@Slf4j
public class googleBoomFilter {

    @Test
    public void guavaTest() {
        long star = System.currentTimeMillis();
       BloomFilter<Integer> filter = BloomFilter.create(
                Funnels.integerFunnel(),
               10000000,
                0.01);
        for (int i = 0; i < 10000000; i++) {
            filter.put(i);
        }
        log.info("" + filter.mightContain(1));
        log.info("" + filter.mightContain(2));
        log.info("" + filter.mightContain(3));
        log.info("" + filter.mightContain(10000000));
        long end = System.currentTimeMillis();
        System.out.println("执行时间：" + (end - star));

    }
}
