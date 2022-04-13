package com.zjm.my_queue;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 延迟队列：例子以订单超过30分钟未付款取消订单为例
 */
@NoArgsConstructor
public class OrderDelay implements Delayed {
    private String orderId;
    //延时时间
    private long timeout;
    //开始时间
    private static long start ;
    public static void  main(String[] args){
        start = System.currentTimeMillis() / 1000;
        DelayQueue<OrderDelay> queue = new DelayQueue<OrderDelay>();
        queue.put(new OrderDelay("订单id",
                TimeUnit.MILLISECONDS.convert(20000, TimeUnit.MILLISECONDS)));
        try {
            queue.take().doBusiness();//获取延时队列并执行业务
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

       /* start = System.currentTimeMillis() / 1000;
        List<String> list = Arrays.asList("00000001", "00000002", "00000003", "00000004", "00000005");
        DelayQueue<OrderDelay> queue = new DelayQueue<OrderDelay>();
        for (int i = 0; i < 5; i++) {
            //添加延时队列，并声明延时时间
            queue.put(new OrderDelay(list.get(i),
                    TimeUnit.NANOSECONDS.convert(10, TimeUnit.SECONDS)));
            try {
                queue.take().doBusiness();//获取延时队列并执行业务
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/
    }

    public OrderDelay(String orderId, long timeout) {
        this.orderId = orderId;
        this.timeout = timeout + System.currentTimeMillis();
    }
    // 返回距离你自定义的超时时间还有多少
    public long getDelay(TimeUnit unit) {
        return unit.convert(timeout - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }
    private void doBusiness() {
        System.out.println("After " + (System.currentTimeMillis() / 1000 - start) + " seconds");
        System.out.println(orderId + "--延时处理任务-----编号的订单要删除啦。。。。");

    }
    @Override
    public int compareTo(Delayed o) {
        return 0;
    }
}