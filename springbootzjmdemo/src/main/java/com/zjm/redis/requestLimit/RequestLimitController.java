package com.zjm.redis.requestLimit;

import com.zjm.util.SpringBeanUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RequestMapping("/requestLimit")
@RestController
@Slf4j
@Api(tags = "Redis限流")
public class RequestLimitController {
    @GetMapping("/limitTest/{period}/{maxCount}")
    @ApiOperation(value = "Redis限流测试(指定时间点最大请求次数)")
    public String limitTest(@ApiParam(name = "period", value = "时间(秒)") @PathVariable("period") Integer period,
                            @ApiParam(name = "maxCount", value = "次数") @PathVariable("maxCount") Integer maxCount) {
         if (checkLimit("zjmUserId","orderPart",period,maxCount)){
             return "请求成功";
         }else {
             return "请求失败，每"+period+"秒，只可请求"+maxCount+"次";
         }
    }

    private boolean checkLimit(String userId, String resourceKey, Integer period, Integer maxCount) {
        RedisTemplate redisTemplate = SpringBeanUtils.getBean("redisTemplate");
        String key = "zjmProject" + userId + resourceKey;
        long nowTs = System.currentTimeMillis();
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        zSetOperations.add(key, "zjm" + nowTs, nowTs);
        zSetOperations.removeRangeByScore(key, 0, nowTs - period * 1000);
        long count = zSetOperations.zCard(key);
        redisTemplate.expire(key, Duration.ofSeconds(period + 1));
        return count <= maxCount;

    }
}
