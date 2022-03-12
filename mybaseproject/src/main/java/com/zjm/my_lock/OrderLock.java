package com.zjm.my_lock;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author zhujianming
 * @description  弃用
 *
 * @date 2022/3/11 16:21
 */
@Slf4j
public class OrderLock {

    List<OrderItem> items;

    private List<OrderItem> createCart() {
        return IntStream.rangeClosed(1, 3)

                .mapToObj(num -> ThreadLocalRandom.current().nextInt(items.size()))

                .map(index -> items.get(index)).collect(Collectors.toList());
    }

    private boolean createOrder(List<OrderItem> orderItems){
        List<ReentrantLock> locks = new ArrayList<>();
        for (OrderItem orderItem : orderItems) {
            try {
                if (orderItem.lock.tryLock(2, TimeUnit.SECONDS)) {
                    locks.add(orderItem.lock);
                } else {
                    locks.forEach(ReentrantLock::unlock);
                    return false;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            orderItems.forEach(item -> item.remain--);
        } finally {
            locks.forEach(ReentrantLock::unlock);
        }

        return true;
    }


    public void test() {
        Long successNum = IntStream.rangeClosed(1, 100).parallel().mapToObj(i -> {
            List<OrderItem> cart = createCart();
            return createOrder(cart);
        }).filter(true2 -> true2).count();

        log.info("successNum:{},totalRemain：{}",successNum,items.stream());


    }
}
@Builder
@Data
class OrderItem {
    private String orderName;
    int remain = 10000;
    ReentrantLock lock = new ReentrantLock();
}