package com.zjm.my_thread.cachedthreadpool;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * @author zhujianming
 * @description
 * @date 2022/3/12 16:54
 */
@Slf4j
public class ThreadPoolExecutor_Test {

    @Test
    public void test() throws InterruptedException {
        //使用一个计数器跟踪完成的任务数

        AtomicInteger atomicInteger = new AtomicInteger();

        /**
         * 创建一个具有2个核心线程、5个最大线程，使用容量为10的ArrayBlockingQueue阻塞队列作为工作队列的线程池，使用默认的AbortPolicy拒绝策略,
         也就是任务添加到线程池失败会抛出 RejectedExecutionException；
         * 程池默认的工作行为：
         * 不会初始化 corePoolSize 个线程，有任务来了才创建工作线程；
         * 当核心线程满了之后不会立即扩容线程池，而是把任务堆积到工作队列中；
         * 当工作队列满了后扩容线程池，一直到线程个数达到 maximumPoolSize 为止；
         * 如果队列已满且达到了最大线程后还有任务进来，按照拒绝策略处理；
         * 当线程数大于核心线程数时，线程等待 keepAliveTime 后还是没有任务需要处理的话，收缩线程到核心线程数。
         *
         * 补充和解决方案：
         * 核心线程数满之后会把新创建的线程方案队列中，队列满之后才扩容直到最大容量，再超出就会拒绝新创建的线程；
         * 如果线程池的线程执行快会把腾出来的队列空间给刚创建的线程；
         * 线程放到队列之前都会先初始化，所以如果队列无限大，初始化线程也是无限大，容易造成内存溢出【OOM】;
         * 当线程数大于核心线程数时，线程等待 keepAliveTime 后还是没有任务需要处理的话，收缩线程到核心线程数,从队列里取出线程到核心线程;
         * 对于执行比较慢、数量不大的 IO 任务，或许要考虑更多的线程数，而不需要太大的队列。
         * 而对于吞吐量较大的计算型任务，线程数量不宜过多，可以是 CPU 核数或核数 *2（理由是，线程一定调度到某个 CPU 进行执行，如果任务本身是 CPU 绑定的任务，那么过多的线程只会增加线程切换的开销，并不能提升吞吐量），
         * 但可能需要较长的队列来做缓冲。
         * 实际运用中为了不出现拒绝新创建线程的情况，可以把最大线程容【maximumPoolSize】量增大，或把阻塞队列【BlockingQueue】增大。
         */
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(

                2, 5,

                2, TimeUnit.SECONDS,

                new ArrayBlockingQueue<>(15),

                new ThreadFactoryBuilder().setNameFormat("demo-threadpool-%d").build(),

                new ThreadPoolExecutor.AbortPolicy());

        //声明线程池后立即调用 prestartAllCoreThreads 方法，来启动所有核心线程
        threadPool.prestartAllCoreThreads();//todo 不知道真正用处

        //传入 true 给 allowCoreThreadTimeOut 方法，来让线程池在空闲的时候同样回收核心线程。
        threadPool.allowCoreThreadTimeOut(true);//todo 不知道真正用处

        printStats(threadPool);

        //每隔1秒提交一次，一共提交20次任务
        IntStream.rangeClosed(1, 20).forEach(i -> {

            try {
                TimeUnit.SECONDS.sleep(4);
            } catch (Exception e) {

                e.printStackTrace();

            }

            int id = atomicInteger.incrementAndGet();

            try {
                threadPool.submit(new MyPoolThread(id));
            } catch (Exception ex) {
                //提交出现异常的话，打印出错信息并为计数器减一
                log.error("出现异常 submitting task {}", id, ex);
                atomicInteger.decrementAndGet();
            }

        });

        TimeUnit.SECONDS.sleep(70);

        log.info("成功次数:{},{}", atomicInteger.intValue(), threadPool.getCorePoolSize());
    }


    /**
     * @description 线程监控
     * @date 2022/3/13 15:26
     */
    private void printStats(ThreadPoolExecutor threadPool) {

        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {

            log.info("=========================");

            log.info("核心线程数量，Pool Size: {}", threadPool.getPoolSize());

            log.info("活跃线程数量，Active Threads: {}", threadPool.getActiveCount());

            log.info("完成的显示数量，Number of Tasks Completed: {}", threadPool.getCompletedTaskCount());

            log.info("线程队列中的数量，Number of Tasks in Queue: {}", threadPool.getQueue().size());

            log.info("=========================");

        }, 0, 5, TimeUnit.SECONDS);

    }
}

@Slf4j
class MyPoolThread implements Runnable {

    private int id;

    public MyPoolThread(int id) {
        this.id = id;
        //log.info("初始化线程:{}", id);
    }

    @Override
    public void run() {
        log.info("{} started:{}", id, Thread.currentThread().getName());
        //每个任务耗时10秒
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
              e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        log.info("{} finished", id);
    }
}
