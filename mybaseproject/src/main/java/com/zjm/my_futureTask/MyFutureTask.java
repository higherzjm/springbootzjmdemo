package com.zjm.my_futureTask;

import com.zjm.jdk8lambda.MyFunction;
import lombok.Builder;
import lombok.Data;
import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.function.Function;

public class MyFutureTask {
    public static void main(String[] args) throws Exception {
        MyFutureTask task=new MyFutureTask();
        task.test1();
    }
    Function<FutureTaskStudents,String> function=null;
    FutureTaskStudents futureTaskStudents=null;

    public void test1() throws Exception{
        //初始化信息
        initInfo();

        //创建线程池
        ExecutorService executor = Executors.newCachedThreadPool();
        FutureTask<String> futureTask = new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                String ret=getFunctionValue(function, futureTaskStudents);
                return ret;
            }
        });
        //启动线程方式1
        //executor.submit(futureTask);
        //启动线程方式2
        futureTask.run();
        //启动线程方式3
        //Thread thread = new Thread(futureTask);
        //thread.start();

        String ret = futureTask.get();

        System.out.println(Thread.currentThread().getName() + " 返回结果:" +ret);
    }

    private void  initInfo(){
        function=new Function<FutureTaskStudents,String>() {
            @Override
            public String apply(FutureTaskStudents o) {
                return o.getNameAge();
            }
        };
        futureTaskStudents=FutureTaskStudents.builder().name("张三").age(100).build();
    }
    private String getFunctionValue(Function<FutureTaskStudents, String> function, FutureTaskStudents students){
        return function.apply(students);
    }
    @Data
    @Builder
    static class FutureTaskStudents {
        private String name;
        private Integer age;
        public String getNameAge(){
            return this.name+":"+age;
        }
    }

}