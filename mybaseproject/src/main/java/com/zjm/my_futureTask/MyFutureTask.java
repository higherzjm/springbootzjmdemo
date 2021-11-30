package com.zjm.my_futureTask;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.function.Function;
@Slf4j
public class MyFutureTask {

    Function<FutureTaskStudents, String> function = null;
    FutureTaskStudents futureTaskStudents = null;
    FutureTask<String> futureTask = null;

    /**
     * futureTask 线程
     */
    @Test
    public void test1_futureTask() throws Exception {
        //初始化信息
        initInfo();
        //启动线程方式1
        futureTask.run();
        String ret = futureTask.get();

        System.out.println(Thread.currentThread().getName() + " 返回结果:" + ret);
    }
    /**
     * ExecutorService 线程
     */
    @Test
    public void test2_executor() {
        //初始化信息
        initInfo();
        //启动线程方式1
        //创建线程池
        ExecutorService executor = Executors.newCachedThreadPool();
        executor.submit(futureTask);
    }
    /**
     * 常规Thread线程
     */
    @Test
    public void test3_baseThread() {
        //初始化信息
        initInfo();
        //启动线程方式3
        Thread thread = new Thread(futureTask);
        thread.start();
    }

    private void initInfo() {
        function = new Function<FutureTaskStudents, String>() {
            @Override
            public String apply(FutureTaskStudents o) {
                return o.getNameAge();
            }
        };
        futureTaskStudents = FutureTaskStudents.builder().name("张三").age(100).build();

        futureTask = new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                String ret = getFunctionValue(function, futureTaskStudents);
                System.out.println("线程返回结果:"+ret);
                return ret;
            }
        });
    }

    private String getFunctionValue(Function<FutureTaskStudents, String> function, FutureTaskStudents students) {
        return function.apply(students);
    }

    @Data
    @Builder
    static class FutureTaskStudents {
        private String name;
        private Integer age;

        public String getNameAge() {
            return this.name + ":" + age;
        }
    }

}