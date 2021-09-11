package com.zjm.my_thread.cachedthreadpool;



import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * �̳߳�
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
     * @Description:  ��ͨ���̻߳��̳߳أ����������ȴ�
     * @Author: zhujianming
     * @Date: 2021/5/8
     * @param:
     * void
     **/
    public void test1() throws ExecutionException, InterruptedException {
        Thread thread = new Thread(new MyThread());
        thread.start();
        System.out.println("�߳�����ִ��------------------");

        //�̳߳�
        ExecutorService cachedThreadPool = Executors.newFixedThreadPool(2);
        cachedThreadPool.execute(new MyThread());

        Future<String> future = cachedThreadPool.submit(new MyTaskCallable("�����й���submit"));
        log.info("ret:" + future.get());
        log.info("�÷���ִ�н���----------------");


    }
    /**
     * @Description: ���߳�ִ�еȴ�������߳���ִ�����֮��ſ�������һ��ִ�С����繤�̲�Ҫ��ȥ10������ȡ��Ǯ֮��ſ���ִ����һ����ҵ��
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
                    log.info("�߳�ִ��:"+System.nanoTime());
                    latch.countDown();//ÿִ����һ�λ��1
                    log.info("submit->count:"+latch.getCount());
                } catch (Exception e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
        try {
            latch.await();//�����ȴ���ֻ��countΪ0��ʱ��ֹͣ�ȴ�
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("ִ�м���->count:"+latch.getCount());
        log.info("-------------ִ�����------------");
    }
}

class MyThread implements Runnable {

    @Override
    public void run() {
        System.out.println("----------�����߳�");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("----------�߳�ִ�н���");
    }
}

class MyTaskCallable implements Callable<String> {
    String param;

    public MyTaskCallable(String param) {
        this.param = param;
    }

    @Override
    public String call() throws Exception {
        System.out.println("----------ִ��Callable�߳�");
        return "��ʼ������:" + param;
    }
}