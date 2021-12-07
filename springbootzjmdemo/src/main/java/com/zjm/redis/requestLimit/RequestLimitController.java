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

/**
 * redis 限流，如没2秒内只能限制10次请求
 */
@RequestMapping("/requestLimit")
@RestController
@Slf4j
@Api(tags = "Redis")
public class RequestLimitController {
    @GetMapping("/limitTest/{period}/{maxCount}")
    @ApiOperation(value = "Redis限流-Redis限流测试(指定时间(秒)最大请求次数)")
    public String limitTest(@ApiParam(name = "period", value = "时间(秒)") @PathVariable("period") Integer period,
                            @ApiParam(name = "maxCount", value = "次数") @PathVariable("maxCount") Integer maxCount) {
        String userId = "zjmUserId";
        String resourceKey = "orderPart";
        if (checkLimit(userId, resourceKey, period, maxCount)) {
            return "请求成功";
        } else {
            return "请求失败，每" + period + "秒只可请求" + maxCount + "次";
        }
    }

    /**
     * 针对资源做限流校验
     * @param userId 用户id
     * @param resourceKey 操作资源
     * @param period 时间(秒)
     * @param maxCount 限制操作次数
     * @return
     */
    private boolean checkLimit(String userId, String resourceKey, Integer period, Integer maxCount) {
        RedisTemplate<String, Object> redisTemplate = SpringBeanUtils.getBean("redisTemplate");
        String key = "zjmProject" + userId + resourceKey;
        //当前毫秒数
        long nowTs = System.currentTimeMillis();
        //获取命令操作对象
        ZSetOperations<String,Object> zSetOperations = redisTemplate.opsForZSet();
        //Add value to a sorted set at key, or update its score if it already exists.
        zSetOperations.add(key, "zjm" + nowTs, nowTs);//[value不能一样，不然都会认为是同一个请求，测不出限流的效果]
        /**
         * Remove elements with scores between min and max from sorted set with key
         * 移除时间窗口(period/秒)之前的行为记录，剩下的都是时间窗口内的记录。
         * 【比如每5秒限制请求2次，就是当前毫秒减去5000秒为时间间隔】
         */
        //zSetOperations.removeRangeByScore(key, 0, nowTs - period * 1000); //[非必要]
        /**
         * Get the size of sorted set with key.
         *获取时间窗口内的行为数量
         */
        long count = zSetOperations.zCard(key);
        /**
         *Set time to live for given key.
         *设置过期时间，避免冷用户持续占用内存.过期时间应该等于时间窗口的长度，再多宽限1秒
         *【相当于是过了指定期间的时间，马上重新开始计时】
         */
        redisTemplate.expire(key, Duration.ofSeconds(period + 1));//[必要，不然会锁住前一次的计时，无法解锁]
        return count <= maxCount;

    }
}
