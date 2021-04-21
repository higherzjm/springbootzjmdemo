package com.zjm.thread.lock;


import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @Description: ����������
 * ������������������ǰ�߳�ִ��ĳ�������Ѿ���ȡ�˸�������ô�ڷ����г����ٴλ�ȡ��ʱ�Ǿͻ��ȡ����������
 * @Author: zhujianming
 * @Date: 2021/4/21
 **/
@Slf4j
public class NoReentryLock {
    Lock lock = new Lock();

    @Test
    public void method1() throws InterruptedException {
        lock.locked();
        method2();
        lock.unlock();
    }

    public void method2() throws InterruptedException {
        lock.locked();
        log.info(String.valueOf(System.currentTimeMillis()));
        lock.unlock();
    }
}
@Slf4j
class Lock {
    private boolean isLocked = false;

    public synchronized void locked() throws InterruptedException {
        Thread thread = Thread.currentThread();
        log.info("threadName��"+thread.getName());
        log.info("isLocked:"+isLocked);
        while (isLocked) {
            wait();
        }
        isLocked = true;
    }

    public synchronized void unlock() {
        isLocked = false;
        notify();
    }
}
