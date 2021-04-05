package com.zjm.sportslottery;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.StringJoiner;
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
     *  http://localhost:8081/sportsLottery/lotteryOf31Choose7
     */
    @RequestMapping("/lotteryOf31Choose7")
    public String lotteryOf31Choose7(){
        Random random=new Random(System.currentTimeMillis());
       int s=random.nextInt(35);
        log.info("s:"+s);
        StringJoiner stringJoiner=new StringJoiner(",");
        List<Integer> dataList=new ArrayList<>();
        for (int i=0;i<7;i++){
            dataList.add(random.nextInt(35));
            stringJoiner.add(String.valueOf(random.nextInt(35)));
        }
        dataList.stream().sorted();

        return dataList.stream().sorted().collect(Collectors.toList()).toString();
    }


    /**
     * @Description: 大乐透（前区“35选5”＋后区“12选2”） 每周一、三、六
     * http://localhost:8081/sportsLottery/lotteryOf35Choose7
     */
    @RequestMapping("/lotteryOf35Choose7")
    public String lotteryOf35Choose7(){

        return "";
    }

}
