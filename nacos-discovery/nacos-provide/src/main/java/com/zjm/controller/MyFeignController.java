package com.zjm.controller;

import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/myFeign")
public class MyFeignController {
    @Autowired
    public StringRedisTemplate redisTemplate;

    @GetMapping("/querySportsLotteryHistoryPrize/{sportsLotteryType}")
    public String studentName(@ApiParam(name = "sportsLotteryType", value = "类型 1：双色球31选7, 2:大乐透31选5+12选2", required = true) @PathVariable("sportsLotteryType") String sportsLotteryType) {
        String redisValue = "";
        if ("1".equals(sportsLotteryType)) {
            redisValue = redisTemplate.opsForValue().get("lotteryOf31Choose7List");

        } else if ("2".equals(sportsLotteryType)) {
            redisValue = redisTemplate.opsForValue().get("lotteryOf35Choose7List");

        }
        return redisValue;
    }
}
