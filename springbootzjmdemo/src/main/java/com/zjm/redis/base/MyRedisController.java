package com.zjm.redis.base;

import io.netty.util.CharsetUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author zhujianming
 */
@RequestMapping("/redis")
@RestController
@Api(tags = "Redis")
public class MyRedisController {
    @Autowired
    private RedisMessageListenerContainer messageListenerContainer;
    @Autowired
    public StringRedisTemplate redisTemplate;
    @Autowired
    private RedisMessageListener redisMessageListener;
    @Autowired
    private RedisExpireMsgListener redisExpireMsgListener;

    @GetMapping("/getValue")
    @ApiOperation(value = "简单应用--读取值")
    public String getValue() {
        String key = "今日新闻";
        String value = redisTemplate.opsForValue().get(key);
        return value;
    }


//--------------------------------------------------------------------------------

    @GetMapping("/topicListener")
    @ApiOperation(value = "简单应用--读取值")
    public String topicListener() {
        String topicName = "今日新闻";
        messageListenerContainer.addMessageListener(redisMessageListener, new ChannelTopic(topicName));
        return "今日新闻--监听";
    }

    @GetMapping("/topicPublish")
    @ApiOperation(value = "简单应用--读取值")
    public String topicPublish() {
        String topicName = "今日新闻";
        String topicValue = "今日新闻--日本发生大地震";
        redisTemplate.getConnectionFactory().getConnection().publish(topicName.getBytes(CharsetUtil.UTF_8), topicValue.getBytes(CharsetUtil.UTF_8));
        return "今日新闻---主题发布";
    }
    @GetMapping("/setValue2")
    @ApiOperation(value = "简单应用--设置无有效期值")
    public String setValue2() {
        String key = "今日新闻";
        String value = "日本发生大地震";
        redisTemplate.opsForValue().set(key, value);
        return key;
    }
//----------------------------------------------------------------------------------------

    @ApiOperation(value = "简单应用--主题过期监听")
    @GetMapping("/topicExpireListener")
    public String topicExpireListener() {
        String topicName = "有效期今日新闻";
        messageListenerContainer.addMessageListener(redisExpireMsgListener, new ChannelTopic(topicName));
        return "有效期今日新闻---监听";
    }
    @GetMapping("/topicExpirePublish")
    @ApiOperation(value = "简单应用--有效期主题发布")
    public String topicExpirePublish() {
        String topicName = "有效期今日新闻";
        String topicValue = "有效期今日新闻---日本发生大地震";
        redisTemplate.expire(topicName, 10, TimeUnit.SECONDS);
        redisTemplate.getConnectionFactory().getConnection().publish(topicName.getBytes(CharsetUtil.UTF_8), topicValue.getBytes(CharsetUtil.UTF_8));
        return "有效期今日新闻---发布";
    }
    @GetMapping("/setValue")
    @ApiOperation(value = "简单应用--设置有效期值")
    public String setValue() {
        String key = "有效期今日新闻";
        String value = "日本发生大地震";
        redisTemplate.opsForValue().set(key, value, 5, TimeUnit.SECONDS);
        return "有效期今日新闻---发布";
    }
//-----------------------------------------------
    @GetMapping("/topicConsumer")
    @ApiOperation(value = "简单应用--主题消费")
    public String topicConsume() {
        String topicName = "有效期今日新闻";
        redisTemplate.getConnectionFactory().getConnection().subscribe(redisMessageListener, topicName.getBytes());
        return "test";
    }


}
