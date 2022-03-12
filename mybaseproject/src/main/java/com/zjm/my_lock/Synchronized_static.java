package com.zjm.my_lock;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.stream.IntStream;

/**
 * @author zhujianming
 * @description  对静态变量和非静态方法加锁的复杂情况
 *  在非静态的 wrong 方法上加锁，只能确保多个线程无法执行同一个实例的 wrong 方法，却不能保证不会执行不同实例的 wrong 方法。
 *  而静态的 counter 在多个实例中共享，所以必然会出现线程安全问题。
 *  解决方法：
 *    在多个线程不同实例操作下，如果非静态方法需要对静态属性同步，可以加静态代码块同步， synchronized (locker)：locker静态属性。
 *
 * @date 2022/3/11 11:28
 */
@Slf4j
public class Synchronized_static {
    @Getter
    private static int counter = 0;
    @Getter
    private  int counter2 = 0;

    private static final Object locker = new Object();

    //多线程执行不同实例，【属性静态方法非静态】
    @Test
    public void test1() {
        Synchronized_static.reset();
        //多线程循环一定次数调用Data类不同实例的wrong方法
        IntStream.rangeClosed(1, 10000).parallel().forEach(i -> new Synchronized_static().wrong());

        log.info("累计值:" + Synchronized_static.getCounter());
    }

    //多线程执行同一实例，【属性静态方法非静态】
    @Test
    public void test2() {
        Synchronized_static.reset();
        Synchronized_static s = new Synchronized_static();
        IntStream.rangeClosed(1, 10000).parallel().forEach(i -> s.wrong2());

        log.info("累计值:" + s.getCounter2());
    }
    //多线程执行不同实例，【属性和方法都是静态的】
    @Test
    public void test3() {
        Synchronized_static.reset();
        IntStream.rangeClosed(1, 10000).parallel().forEach(i -> Synchronized_static.wrong3());

        log.info("累计值:" + Synchronized_static.getCounter());
    }



    private static int reset() {

        counter = 0;

        return counter;

    }

  /**
    在非静态的 wrong 方法上加锁，只能确保多个线程无法执行同一个实例的 wrong 方法，却不能保证不会执行不同实例的 wrong 方法。
     而静态的 counter 在多个实例中共享，所以必然会出现线程安全问题
   */
    private void wrong() {
        //因为counter是静态属性，如果这里不加静态搜，会造成线程冲突，最终结果不对
        synchronized (locker) {
            counter++;
        }
    }

    private synchronized void wrong2() {
        counter2++;
    }
    private static synchronized void wrong3() {
        counter++;
    }
}
