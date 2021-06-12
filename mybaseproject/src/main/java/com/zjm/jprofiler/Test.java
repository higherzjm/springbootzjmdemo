package com.zjm.jprofiler;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
@Slf4j
public class Test {

    static class TestBean {
        public TestBean() {
        }
    }

    public static void main(String[] args) {
        System.out.println("test JProfiler start");
        try {
            Thread.sleep(1000 * 20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List list = new ArrayList();
        for (int i = 0; i < 100000; i++) {
            Test.TestBean tt = new Test.TestBean();
            list.add(tt);
            try {
                Thread.sleep(1000 * 4);
                log.info("i="+i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("test JProfiler end");
    }

}
