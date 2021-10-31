package com.zjm.my_futureTask;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class MyFutureTask {
    public static void main(String[] args) throws Exception {
        //�����̳߳�
        ExecutorService executor = Executors.newCachedThreadPool();
        FutureTask<String> futureTask = new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println(Thread.currentThread().getName() + ":" + "��ʼ�տ�ˮ...");
                // ģ���տ�ˮ��ʱ
                Thread.sleep(2000);
                System.out.println(Thread.currentThread().getName() + ":" + "��ˮ�Ѿ��պ���...");
                return "��ˮ";
            }
        });
        //�����̷߳�ʽ1
        //executor.submit(futureTask);
        //�����̷߳�ʽ2
        futureTask.run();
        //�����̷߳�ʽ3
        //Thread thread = new Thread(futureTask);
        //thread.start();

        // do other thing
        System.out.println(Thread.currentThread().getName() + ":" + " ��ʱ������һ���߳�ִ��future���߼����տ�ˮ������ʱ���ǿ��Ըɵ������飨����׼�����ʳ�ģ�...");
        // ģ��׼�����ʳ�ĺ�ʱ
        Thread.sleep(3000);
        System.out.println(Thread.currentThread().getName() + ":" + "���ʳ��׼������");
        String shicai = "���ʳ��";

        // ��ˮ�Ѿ��Ժã�����ȡ���պõĿ�ˮ
        //��ȡfutureTaskִ�н��
        String boilWater = futureTask.get();

        System.out.println(Thread.currentThread().getName() + ":" + boilWater + "��" + shicai + "�Ѿ�׼���ã����ǿ��Կ�ʼ������");
    }
}