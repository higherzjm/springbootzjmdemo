package com.zjm.thread.cachedthreadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/** 线程池
 * @author zhujianming
 */
@Slf4j
public class CachedThreadPool {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CachedThreadPool cachedThreadPool = new CachedThreadPool();
        cachedThreadPool.test1();
    }


    public void test1() throws ExecutionException, InterruptedException {
        Thread thread = new Thread(new MyThread());
        thread.start();
        System.out.println("线程正在执行------------------");

        //线程池
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        cachedThreadPool.execute(new MyThread());

        Future<String> future=cachedThreadPool.submit(new MyTaskCallable("我是中国人submit"));
        log.info("ret:"+future.get());

    }
}

class MyThread implements Runnable {

    @Override
    public void run() {
        System.out.println("----------进入线程");
        try {
            Thread.sleep(6000);
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