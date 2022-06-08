package com.zjm.my_keyword;

import java.util.Map;

/**
 * @author zhujianming
 * @description
 * @date 2022/2/28 15:12
 */
public class My_volatile {
    //共享变量
    private static boolean initFlag = false;

    public static void main(String[] args) throws InterruptedException {
        My_volatile  my_volatile=new My_volatile();
        my_volatile.test1();
    }

    public void test1() throws InterruptedException {
        //做业务的线程
        new Thread(() -> {
            System.out.println("等待数据。。。。");
            while (!initFlag) {
                //如果中间打印一句话会失效，可能虚拟机把任务交出去重新取回会从主内存获取值
            }
            System.out.println("工作完成了");
        }).start();
        //保证第一个线程先执行
        Thread.sleep(2000);
        //准备数据的线程
        new Thread(() -> {
            System.out.println("开始准备数据。。。");
            initFlag = true;
            System.out.println("数据准备完毕。。。");
        }).start();
    }
}
