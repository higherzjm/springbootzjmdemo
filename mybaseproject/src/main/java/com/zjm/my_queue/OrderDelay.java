package com.zjm.my_queue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 延迟队列：例子以订单超过30分钟未付款取消订单为例
 */
public class OrderDelay implements Delayed {
    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        list.add("00000001");
        list.add("00000002");
        list.add("00000003");
        list.add("00000004");
        list.add("00000005");

        DelayQueue<OrderDelay> queue = new DelayQueue<OrderDelay>();

        long start = System.currentTimeMillis()/1000;

        for(int i = 0;i<5;i++){
            //添加延时队列，并声明延时时间
            queue.put(new OrderDelay(list.get(i),
                    TimeUnit.NANOSECONDS.convert(3,TimeUnit.SECONDS)));

            try {
                queue.take().doBusiness();

                System.out.println("After " +(System.currentTimeMillis()/1000-start) + " seconds");

            } catch (InterruptedException e) {
                e.printStackTrace();

            }
        }

    }
    private String orderId;

    private long timeout;

    OrderDelay(String orderId, long timeout) {
        this.orderId = orderId;
        this.timeout = timeout + System.nanoTime();
    }

    public int compareTo(Delayed other) {
        if (other == this)
            return 0;
        OrderDelay t = (OrderDelay) other;
        long d = (getDelay(TimeUnit.NANOSECONDS) - t.getDelay(TimeUnit.NANOSECONDS));
        return (d == 0) ? 0 : ((d < 0) ? -1 : 1);

    }

    // 返回距离你自定义的超时时间还有多少

    public long getDelay(TimeUnit unit) {
        return unit.convert(timeout - System.nanoTime(),TimeUnit.NANOSECONDS);
    }

    void doBusiness() {

        System.out.println(orderId+"--延时处理任务-----编号的订单要删除啦。。。。");

    }

}