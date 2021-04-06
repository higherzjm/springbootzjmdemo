package com.zjm.sportslottery;

import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @author zhujianming
 * 体育彩票专题
 */
@RequestMapping("/sportsLottery")
@RestController
@Slf4j
public class SportsLotteryController {
    @Autowired
    public StringRedisTemplate redisTemplate;

    /**
     * @Description: 福建体彩31选7 每周一、三、六,每日开奖
     * http://localhost:8081/sportsLottery/lotteryOf31Choose7
     */
    @RequestMapping("/lotteryOf31Choose7")
    public String lotteryOf31Choose7() {
        Random random = new Random(System.currentTimeMillis());
        List<Integer> dataList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            Integer data = random.nextInt(32);
            addValue(data, dataList, 0);
        }
        ComparatorList cl = new ComparatorList();
        dataList.sort(cl);
        return dataList.toString();
    }

    public void addValue(Integer value, List<Integer> valueList, int m) {
        if (value != 0 && !valueList.contains(value)) {
            valueList.add(value);
        } else {
            Random random = new Random(System.currentTimeMillis() + m);
            Integer data = random.nextInt(32);
            log.info("data:" + data);
            m++;
            addValue(data, valueList, m);
        }
    }


    /**
     * @Description: 大乐透（前区“35选5”＋后区“12选2”） 每周一、三、六
     * http://localhost:8081/sportsLottery/lotteryOf35Choose7
     */
    @RequestMapping("/lotteryOf35Choose7")
    public String lotteryOf35Choose7() {
        List<Integer> dataList = new ArrayList<>();
        Random random = new Random(System.currentTimeMillis());
        List<Integer> dataList5 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Integer data = random.nextInt(36);
            addValue(data, dataList5, 0);
        }
        ComparatorList cl = new ComparatorList();
        dataList5.sort(cl);

        List<Integer> dataList2 = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Integer data = random.nextInt(13);
            addValue(data, dataList2, 0);
        }
        dataList2.sort(cl);

        dataList.addAll(dataList5);
        dataList.addAll(dataList2);
        return dataList.toString();
    }

}

class ComparatorList implements Comparator<Integer> {
    @Override
    public int compare(Integer o1, Integer o2) {
        // 这里返回的值，1升序 -1降序
        return o1 > o2 ? 1 : -1;
    }

}