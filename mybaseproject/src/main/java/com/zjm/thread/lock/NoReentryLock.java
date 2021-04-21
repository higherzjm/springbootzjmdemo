package com.zjm.thread.lock;


/**
 * @Description: 不可重入锁
 * @Author: zhujianming
 * @Date: 2021/4/21
 **/
public class NoReentryLock {
    Lock lock = new Lock();

    public void print() throws InterruptedException {
        lock.locked();
        doAdd();
        lock.unlock();
    }

    public void doAdd() throws InterruptedException {
        lock.locked();
        // dosomething
        lock.unlock();
    }
}

class Lock {
    private boolean isLocked = false;

    public synchronized void locked() throws InterruptedException {
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
