package com.zjm.redis.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Tuple;

import java.util.Calendar;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis 延时订单测试：例子以订单超过30分钟未付款取消订单为例
 */
@Slf4j
public class RedisOrderDelayTest {
    private static final String ADDR = "127.0.0.1";
    private static final int PORT = 6379;
    private static JedisPool jedisPool = new JedisPool(ADDR, PORT);

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

    private static Jedis getJedis() {
        return jedisPool.getResource();
    }

    //生产者,生成5个订单放进去
    void productionDelayMessage() {
        for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep(1000);//每隔1秒发一次任务
            } catch (Exception e) {
                e.printStackTrace();
            }
            Calendar cal1 = Calendar.getInstance();
            if (i == 0) {
                cal1.add(Calendar.SECOND, 20);//延时20秒
            } else if (i == 1) {
                cal1.add(Calendar.SECOND, 30);//延时30秒
            } else {
                cal1.add(Calendar.SECOND, 40);//延时2秒
            }
            int expireSecond = (int) (cal1.getTimeInMillis() / 1000);
            String orderId = "orderId-" + i;
            RedisOrderDelayTest.getJedis().zadd("orderId", expireSecond, orderId);
            log.info("redis生成了一个订单任务:{},过期时钟秒数:{}", orderId, expireSecond);
        }
    }

    //消费者，取订单
    void consumerDelayMessage() {
        Jedis jedis = RedisOrderDelayTest.getJedis();
        Long start = System.currentTimeMillis() / 1000;
        while (true) {//死循环监听信息
            if (System.currentTimeMillis() / 1000 - start > 120) {
                break;
            }
            Set<Tuple> items = jedis.zrangeWithScores("orderId", 0, 1);
            if (items == null || items.isEmpty()) {
                // System.out.println("当前没有等待的任务");
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                continue;
            }
            //过期秒数
            int expireSecond = (int) ((Tuple) items.toArray()[0]).getScore();
            String orderId = ((Tuple) items.toArray()[0]).getElement();
            if (orderId.equals("orderId-0")) {
                try {
                    //休眠21秒，此任务过期为20秒，会变为过期任务
                    TimeUnit.SECONDS.sleep(21);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Calendar cal = Calendar.getInstance();

            int nowSecond = (int) (cal.getTimeInMillis() / 1000);
            if (orderId.equals("orderId-0")) {
                log.info("nowSecond:{},expireSecond:{}", nowSecond, expireSecond);
            }
            //当前秒数小于过期秒数，说明为未过期任务
            if (nowSecond<expireSecond) {
                jedis.zrem("orderId", orderId);
                log.info("消费任务:{},nowSecond:{},expireSecond:{}", orderId, nowSecond, expireSecond);
            } else {
                jedis.zrem("orderId", orderId);
                log.info("过期任务:{},nowSecond:{},expireSecond:{}", orderId, nowSecond, expireSecond);
            }
        }
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