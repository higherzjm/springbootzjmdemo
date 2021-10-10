package com.zjm.redis.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Tuple;

import java.util.Calendar;
import java.util.Set;

/**
 * redis 延时订单测试：例子以订单超过30分钟未付款取消订单为例
 */
public class RedisOrderDelayTest {

    private static final String ADDR = "127.0.0.1";

    private static final int PORT = 6379;

    private static JedisPool jedisPool = new JedisPool(ADDR, PORT);

    public static Jedis getJedis() {

        return jedisPool.getResource();

    }

    //生产者,生成5个订单放进去

    public void productionDelayMessage() {

        for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep(1000);//每隔1秒发一次任务
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Calendar cal1 = Calendar.getInstance();
            if (i == 0) {
                cal1.add(Calendar.SECOND, 20);
            } else if (i == 1) {
                cal1.add(Calendar.SECOND, 30);
            } else {
                cal1.add(Calendar.SECOND, 2);
            }

            int second3later = (int) (cal1.getTimeInMillis() / 1000);

            RedisOrderDelayTest.getJedis().zadd("OrderId", second3later, "OID0000001--" + i);

            System.out.println(System.currentTimeMillis() / 1000 + ":redis生成了一个订单任务：订单ID为" + "OID0000001--" + i + "----" + second3later);

        }

    }

    //消费者，取订单
    public void consumerDelayMessage() {
        Jedis jedis = RedisOrderDelayTest.getJedis();
        while (true) {//会进去死循环无限等待
            Set<Tuple> items = jedis.zrangeWithScores("OrderId", 0, 1);
            if (items == null || items.isEmpty()) {
                System.out.println("当前没有等待的任务");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }

            int score = (int) ((Tuple) items.toArray()[0]).getScore();
            Calendar cal = Calendar.getInstance();

            int nowSecond = (int) (cal.getTimeInMillis() / 1000);

            if (nowSecond >= score) {
                String orderId = ((Tuple) items.toArray()[0]).getElement();
                jedis.zrem("OrderId", orderId);
                System.out.println(System.currentTimeMillis() / 1000 + ":redis消费了一个任务：消费的订单OrderId为" + orderId);

            }

        }

    }

    public static void main(String[] args) {

        RedisOrderDelayTest appTest = new RedisOrderDelayTest();
        appTest.product(appTest);
        appTest.consumer(appTest);

    }

    private void product(RedisOrderDelayTest appTest) {
        Thread thread = new Thread(Product.builder().redisOrderDelayTest(appTest).build());
        thread.start();
    }

    private void consumer(RedisOrderDelayTest appTest) {
        Thread thread = new Thread(Consumer.builder().redisOrderDelayTest(appTest).build());
        thread.start();
    }

}

@Data
@Builder
@AllArgsConstructor
class Product implements Runnable {//生产者线程
    private RedisOrderDelayTest redisOrderDelayTest;

    @Override
    public void run() {
        redisOrderDelayTest.productionDelayMessage();
    }
}

@Data
@Builder
@AllArgsConstructor
class Consumer implements Runnable {//消费者线程
    private RedisOrderDelayTest redisOrderDelayTest;

    @Override
    public void run() {
        redisOrderDelayTest.consumerDelayMessage();
    }
}