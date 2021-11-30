package com.zjm.my_netty;

import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import io.netty.util.concurrent.Promise;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Promise与Future的角色区别
 * 关键做到执行过程与结果分开
 * future 监听结果
 * promise 执行算法
 */
@Slf4j
public class PromiseTest {
    public static void main(String[] args){
        PromiseTest promiseTest=new PromiseTest();
        promiseTest.test1();
        //promiseTest.test2();
    }
    public void test1(){
        PromiseTest testPromise = new PromiseTest();
        Promise<String> promise = testPromise.doSomething("哈哈");
        FutureListener futureListener = new FutureListener() {
            @Override
            public void operationComplete(Future future) throws Exception {
                System.out.println(promise.get() + ", something is done");
                if (future.isSuccess()&&future.isDone()) {
                    Object retMsg = future.get();
                    log.info("something is success and is done retMsg:" + retMsg);
                }

            }

        };
        promise.addListener(futureListener);
    }

    public void test2() {
        PromiseTest testPromise = new PromiseTest();
        Promise<String> promise = testPromise.doSomething("哈哈");
        promise.addListener(future -> System.out.println(promise.get()+", something is done"));
    }

    /**
     * 创建一个DefaultPromise并返回，将业务逻辑放入线程池中执行
     *
     * @param value
     * @return
     */
    private Promise<String> doSomething(String value) {
        NioEventLoopGroup loop = new NioEventLoopGroup();
        DefaultPromise<String> promise = new DefaultPromise<>(loop.next());
        loop.schedule(() -> {
            try {
                Thread.sleep(1000);
                promise.setSuccess("执行成功。" + value);
                return promise;
            } catch (InterruptedException ignored) {
                promise.setFailure(ignored);
            }
            return promise;
        }, 0, TimeUnit.SECONDS);
        return promise;
    }

}