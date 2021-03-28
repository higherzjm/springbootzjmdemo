package com.zjm.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zhujianming
 */
public class CachedThreadPool {
    public static void main(String[] args) {
        CachedThreadPool cachedThreadPool = new CachedThreadPool();
        cachedThreadPool.test1();
    }


    public void test1() {
        Thread thread = new Thread(new MyThread());
        thread.start();
        System.out.println("线程正在执行------------------");

        //线程池
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        cachedThreadPool.execute(new MyThread());

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