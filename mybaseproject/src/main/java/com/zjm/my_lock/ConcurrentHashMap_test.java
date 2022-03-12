package com.zjm.my_lock;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

/**
 * @author zhujianming
 * @description
 * @date 2022/3/10 17:26
 */
@Slf4j
public class ConcurrentHashMap_test {
    //线程个数
    private static int THREAD_COUNT = 10;

    //总元素数量
    private static int ITEM_COUNT = 1000;

    //帮助方法，用来获得一个指定元素数量模拟数据的ConcurrentHashMap
    private java.util.concurrent.ConcurrentHashMap<String, Long> getData(int count) {

        return LongStream.rangeClosed(1, count)

                .boxed()

                .collect(Collectors.toConcurrentMap(i -> UUID.randomUUID().toString(), Function.identity(),

                        (o1, o2) -> o1, java.util.concurrent.ConcurrentHashMap::new));
    }

    @Test
    public void wrong() throws InterruptedException {

        java.util.concurrent.ConcurrentHashMap<String, Long> concurrentHashMap = getData(ITEM_COUNT - 100);

        //初始900个元素
        log.info("init size:{}", concurrentHashMap.size());

        ForkJoinPool forkJoinPool = new ForkJoinPool(THREAD_COUNT);

        //使用线程池并发处理逻辑
        forkJoinPool.execute(() -> IntStream.rangeClosed(1, 10).parallel().forEach(i -> {
            synchronized (concurrentHashMap) {
                //查询还需要补充多少个元素
                int gap = ITEM_COUNT - concurrentHashMap.size();

                log.info("gap size:{},{},{}", gap, i, 00);

                //补充元素
                concurrentHashMap.putAll(getData(gap));
            }

        }));

        //等待所有任务完成
        forkJoinPool.shutdown();

        forkJoinPool.awaitTermination(1, TimeUnit.HOURS);

        //最后元素个数会是1000吗？
        log.info("finish size:{}", concurrentHashMap.size());
    }
}
