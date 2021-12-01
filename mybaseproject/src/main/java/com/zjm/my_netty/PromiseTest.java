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
        promiseTest.test1();
    }

    public void test1() throws ExecutionException, InterruptedException {
        Future<String> promise = promiseFuture("哈哈");
        FutureListener futureListener = new FutureListener() {
            @Override
            public void operationComplete(Future future) throws Exception {
                if (future.isSuccess() && future.isDone()) {
                    Object retMsg = future.get();
                    Object now = future.getNow();
                    log.info("监听器operationComplete(..)打印结果:" + retMsg);
                    log.info("now:" + now);
                }

            }

        };
        //执行算法添加返回结果监听
        promise.addListener(futureListener);
        log.info(promise.get() + ", 添加监听器后打印的结果");
        log.info("任务提交完成");

    }


    /**
     *基础Promise
     * @param value
     * @return
     */
    private Promise<String> basePromise(String value) {
        NioEventLoopGroup loop = new NioEventLoopGroup();
        DefaultPromise<String> promise = new DefaultPromise<>(loop.next());
        loop.schedule(new Callable<String>() {
            @Override
            public String call() throws Exception {
                try {
                    Thread.sleep(5000);
                    promise.setSuccess("执行成功。" + value);
                    return promise.get();
                } catch (InterruptedException ignored) {
                    promise.setFailure(ignored);
                }
                return promise.get();
            }
        }, 0, TimeUnit.SECONDS);
        return promise;
    }

    /**
     * netty融合FutureTask
     */
    private Promise<String> promiseFuture(String value) {
        io.netty.channel.nio.NioEventLoopGroup loop = new NioEventLoopGroup();
        io.netty.util.concurrent.DefaultPromise<String> promise = new DefaultPromise<>(loop.next());

        java.util.concurrent.FutureTask futureTask = new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                try {
                    Thread.sleep(5000);
                    promise.setSuccess("执行成功。" + value);
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