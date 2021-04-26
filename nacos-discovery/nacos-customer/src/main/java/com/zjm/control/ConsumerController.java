package com.zjm.control;

import com.alibaba.fastjson.JSON;
import com.zjm.client.IMyFeinService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Administrator
 * @version 1.0
 **/
@RestController
public class ConsumerController {

    //动态代理对象，内部远程调用服务生产者
    @Autowired
    private IMyFeinService myFeinService;
    @Autowired
    public StringRedisTemplate redisTemplate;

    //http://localhost:8091/feignServiceTest/钟南山
    @GetMapping("/feignServiceTest/{name}")
    public String feignServiceTest(@PathVariable("name") String name) {
        String feignServiceTest = myFeinService.studentName(name);
        return "feign跨服务调用测试:" + feignServiceTest;
    }

    @GetMapping("/querySportsLotteryHistoryPrize/{sportsLotteryType}")
    public String querySportsLotteryHistoryPrize(@ApiParam(name = "sportsLotteryType", value = "类型 1：双色球31选7, 2:大乐透31选5+12选2", required = true) @PathVariable("sportsLotteryType") String sportsLotteryType) {
        String sportsLotteryType2 = "";
        if ("1".equals(sportsLotteryType)) {
            sportsLotteryType2 = "lotteryOf31Choose7List";
        } else if ("2".equals(sportsLotteryType)) {
            sportsLotteryType2 = "lotteryOf35Choose7List";
        }
        String sportsLotteryHistoryPrize = redisTemplate.opsForValue().get(sportsLotteryType2);
        if (StringUtils.isEmpty(sportsLotteryHistoryPrize)) {
            sportsLotteryHistoryPrize = myFeinService.querySportsLotteryHistoryPrize(sportsLotteryType);
            redisTemplate.opsForValue().set(sportsLotteryType2, sportsLotteryHistoryPrize);
        }


        return "彩票历史消息:" + sportsLotteryHistoryPrize;
    }
}
