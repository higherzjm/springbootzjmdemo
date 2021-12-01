package com.zjm.my_netty;

import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import io.netty.util.concurrent.Promise;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * Promise与Future的角色区别
 * 关键做到执行过程与结果分开
 * future 监听结果
 * promise 执行算法
 */
@Slf4j
public class PromiseTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        PromiseTest promiseTest = new PromiseTest();
        promiseTest.mainBegin();
    }

    public void mainBegin() {
        for (int i = 0; i < 2; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int finalI = i;
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    log.info("start ThreadId:"+Thread.currentThread().getId());
                    PromiseTest promiseTest = new PromiseTest();
                    try {
                        promiseTest.testStart(finalI);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
        }

    }

    public void testStart(int i) throws ExecutionException, InterruptedException {
        final CountDownLatch l = new CountDownLatch(1000);
        Future<String> promise = promiseFuture("学生",i);
        FutureListener futureListener = new FutureListener() {
            @Override
            public void operationComplete(Future future) throws Exception {//业务执行完才会进来，TODO，需要研究为什么
                if (future.isSuccess() && future.isDone()) {
                    log.info("success-->ThreadId:"+Thread.currentThread().getId());
                    Object retMsg = future.get();
                    Object now = future.getNow();
                    log.info("监听器operationComplete(..)打印结果:" + retMsg);
                    log.info("now:" + now);
                } else {
                    log.info("countDown-->ThreadId:"+Thread.currentThread().getId());
                    l.countDown();
                }

            }

        };
        //执行算法添加返回结果监听
        promise.addListener(futureListener);
        log.info(promise.get() + ", 添加监听器后打印的结果");

    }

    

    /**
     * netty融合FutureTask
     */
    private Promise<String> promiseFuture(String value,int i) {
        io.netty.channel.nio.NioEventLoopGroup loop = new NioEventLoopGroup();
        io.netty.util.concurrent.DefaultPromise<String> promise = new DefaultPromise<>(loop.next());

        java.util.concurrent.FutureTask futureTask = new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                try {
                    if (i==1){
                        Thread.sleep(3000);
                    }else {
                        Thread.sleep(8000);
                    }
                    promise.setSuccess("执行成功,第 "+i+" 个" + value);
                    return promise.get();
                } catch (InterruptedException ignored) {
                    promise.setFailure(ignored);
                }
                return promise.get();
            }
        });
        loop.schedule(futureTask, 0, TimeUnit.SECONDS);
        return promise;
    }
}