package com.zjm.base.sportslottery.util;

import java.util.Comparator;

public class ComparatorList implements Comparator<Integer> {
    @Override
    public int compare(Integer o1, Integer o2) {
        // 这里返回的值，1升序 -1降序
        return o1 > o2 ? 1 : -1;
    }

}