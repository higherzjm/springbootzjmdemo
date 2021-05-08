package com.zjm.thread.cachedthreadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * 线程池
 *
 * @author zhujianming
 */
@Slf4j
public class CachedThreadPool {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CachedThreadPool cachedThreadPool = new CachedThreadPool();
        cachedThreadPool.test1();
        //cachedThreadPool.test2();
    }

    /**
     * @Description:  普通的线程或线程池，无需阻塞等待
     * @Author: zhujianming
     * @Date: 2021/5/8
     * @param:
     * void
     **/
    public void test1() throws ExecutionException, InterruptedException {
        Thread thread = new Thread(new MyThread());
        thread.start();
        System.out.println("线程正在执行------------------");

        //线程池
        ExecutorService cachedThreadPool = Executors.newFixedThreadPool(2);
        cachedThreadPool.execute(new MyThread());

        Future<String> future = cachedThreadPool.submit(new MyTaskCallable("我是中国人submit"));
        log.info("ret:" + future.get());
        log.info("该方法执行结束----------------");

    }
    /**
     * @Description: 多线程执行等待，多个线程数执行完毕之后才可以往下一步执行。比如工程部要求去10个银行取完钱之后才可以执行下一步的业务
     * @Author: zhujianming
     * @Date: 2021/5/8
     * @param:
     * void
     **/
    public void  test2(){
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        int count=10;
        CountDownLatch latch = new CountDownLatch(count);
        for(int i = 0;i< 10; i++){
            executorService.submit(() -> {
                try {
                    Thread.sleep(2000);
                    log.info("线程执行:"+System.nanoTime());
                    latch.countDown();//每执行完一次或减1
                    log.info("submit->count:"+latch.getCount());
                } catch (Exception e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
        try {
            latch.await();//阻塞等待，只到count为0的时候停止等待
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("执行技术->count:"+latch.getCount());
        log.info("-------------执行完毕------------");
    }
}

class MyThread implements Runnable {

    @Override
    public void run() {
        System.out.println("----------进入线程");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("----------线程执行结束");
    }
}

class MyTaskCallable implements Callable<String> {
    String param;

    public MyTaskCallable(String param) {
        this.param = param;
    }

    @Override
    public String call() throws Exception {
        System.out.println("----------执行Callable线程");
        return "初始化参数:" + param;
    }
}