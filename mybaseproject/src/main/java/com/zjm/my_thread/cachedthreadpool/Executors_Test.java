package com.zjm.my_thread.cachedthreadpool;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.UUID;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author zhujianming
 * @description
 * @date 2022/3/14 9:48
 */
@Slf4j
public class Executors_Test {
    /**
     * @description 任务队列是无界的。如果任务较多并且执行较慢的话，队列可能会快速积压，撑爆内存导致 OOM。
     * @date 2022/3/14 10:07
     */
    @Test
    public void test_newFixedThreadPool() throws InterruptedException {
        ThreadPoolExecutor threadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
        threadPool=new ThreadPoolExecutor(1, 1,
                1, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());

        for (int i = 0; i < 100000000; i++) {
            threadPool.execute(new ExecutorThread());
        }
        threadPool.shutdown();

        threadPool.awaitTermination(1, TimeUnit.MILLISECONDS);
    }

    @Test
    public void test_newCachedThreadPool() throws InterruptedException {
        ThreadPoolExecutor threadPool = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        threadPool=new ThreadPoolExecutor(20, Integer.MAX_VALUE,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>());

        for (int i = 0; i < 100000000; i++) {
            threadPool.execute(new ExecutorThread());
        }
        threadPool.shutdown();

        threadPool.awaitTermination(1, TimeUnit.MINUTES);

    }

    class ExecutorThread implements Runnable {

        @Override
        public void run() {
            String payload = IntStream.rangeClosed(1, 100)

                    .mapToObj(__ -> "a")

                    .collect(Collectors.joining("")) + UUID.randomUUID().toString();

            System.out.println("info:" + payload);
            try {
                TimeUnit.MILLISECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
