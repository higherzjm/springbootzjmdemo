package com.zjm.variousAlgorithms.bloomFilters;


import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: 自定义布隆过滤器
 * @Author: zhujianming
 * @Date: 2021/4/17
 **/
@Slf4j
public class CustomerBloomFilters {
    /**
     * 数组长度
     */
    private int arraySize;
    /**
     * 数组
     */
    private int[] array;
    private List<Integer> list = Arrays.asList(new Integer[1000]);

    public static void main(String[] args) {
        CustomerBloomFilters customerBloomFilters = new CustomerBloomFilters();
        customerBloomFilters.test2(customerBloomFilters);
    }

    @Test
    public void test() {
       /* CustomerBloomFilters customerBloomFilters = new CustomerBloomFilters();
        customerBloomFilters.add("name");
        customerBloomFilters.add("age");
        customerBloomFilters.add("birthday");
        customerBloomFilters.add("city");
        log.info("array length:" + array.length + ":" + Arrays.toString(array));
        log.info("name  hashcode_1:" + customerBloomFilters.hashcode_1("name"));
        log.info("name  exist?:" + customerBloomFilters.check("name"));
        for (Integer a : array) {
            if (a != null && a == 1) {
                log.info("a:" + a);
            }
        }
        List<Integer> list2 = list.stream().filter(s -> s != null && s.equals(1)).collect(Collectors.toList());
        log.info("list2:" + list2);*/

    }

    public void test2(CustomerBloomFilters customerBloomFilters) {
        customerBloomFilters.add("name");
        customerBloomFilters.add("age");
        customerBloomFilters.add("birthday");
        customerBloomFilters.add("city");
        log.info("array length:" + array.length + ":" + Arrays.toString(array));
        log.info("name  hashcode_1:" + customerBloomFilters.hashcode_1("name"));
        log.info("name  exist?:" + customerBloomFilters.check("name"));
        for (Integer a : array) {
            if (a != null && a == 1) {
                log.info("a:" + a);
            }
        }
        List<Integer> list2 = list.stream().filter(s -> s != null && s.equals(1)).collect(Collectors.toList());
        log.info("list2:" + list2);

    }

    public CustomerBloomFilters() {
        arraySize = 1000;
        array = new int[arraySize];


    }


    /**
     * 写入数据
     *
     * @param key
     */
    public void add(String key) {
        int first = hashcode_1(key);
        int second = hashcode_2(key);
        int third = hashcode_3(key);
        log.info(key + ":" + first % arraySize + ";" + second % arraySize + ";" + third % arraySize);
        array[first % arraySize] = 1;
        array[second % arraySize] = 1;
        array[third % arraySize] = 1;

        list.set(first % arraySize, 1);
        list.set(second % arraySize, 1);
        list.set(third % arraySize, 1);
    }

    /**
     * 判断数据是否存在
     *
     * @param key
     * @return
     */
    public boolean check(String key) {
        int first = hashcode_1(key);
        int second = hashcode_2(key);
        int third = hashcode_3(key);
        int firstIndex = array[first % arraySize];
        if (firstIndex == 0) {
            return false;
        }
        int secondIndex = array[second % arraySize];
        if (secondIndex == 0) {
            return false;
        }
        int thirdIndex = array[third % arraySize];
        if (thirdIndex == 0) {
            return false;
        }
        return true;
    }

    /**
     * hash 算法1
     *
     * @param key
     * @return
     */
    private int hashcode_1(String key) {
        int hash = 0;
        int i;
        for (i = 0; i < key.length(); ++i) {
            hash = 33 * hash + key.charAt(i);
        }
        return Math.abs(hash);
    }

    /**
     * hash 算法2
     *
     * @param data
     * @return
     */
    private int hashcode_2(String data) {
        final int p = 16777619;
        int hash = (int) 2166136261L;
        for (int i = 0; i < data.length(); i++) {
            hash = (hash ^ data.charAt(i)) * p;
        }
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;
        return Math.abs(hash);
    }

    /**
     * hash 算法3
     *
     * @param key
     * @return
     */
    private int hashcode_3(String key) {
        int hash, i;
        for (hash = 0, i = 0; i < key.length(); ++i) {
            hash += key.charAt(i);
            hash += (hash << 10);
            hash ^= (hash >> 6);
        }
        hash += (hash << 3);
        hash ^= (hash >> 11);
        hash += (hash << 15);
        return Math.abs(hash);
    }
}