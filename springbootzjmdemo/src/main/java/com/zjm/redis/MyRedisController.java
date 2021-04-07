package com.zjm.redis;

import io.netty.util.CharsetUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author zhujianming
 */
@RequestMapping("/redis")
@RestController
@Api(tags = "Redis应用")
public class MyRedisController {
    @Autowired
    private RedisMessageListenerContainer messageListenerContainer;
    @Autowired
    public StringRedisTemplate redisTemplate;
    @Autowired
    private RedisMessageListener redisMessageListener;
    @Autowired
    private RedisExpireMsgListener redisExpireMsgListener;

    //http://localhost:8081/redis/getValue
    @RequestMapping("/getValue")
    public String getValue() {
        String key = "今日新闻";
        String value = redisTemplate.opsForValue().get(key);
        return value;
    }


//--------------------------------------------------------------------------------

    //http://localhost:8081/redis/topicListener  主题监听
    @RequestMapping("/topicListener")
    public String topicListener() {
        String topicName = "今日新闻";
        messageListenerContainer.addMessageListener(redisMessageListener, new ChannelTopic(topicName));
        return "今日新闻--监听";
    }

    //http://localhost:8081/redis/topicPublish  主题发布
    @RequestMapping("/topicPublish")
    public String topicPublish() {
        String topicName = "今日新闻";
        String topicValue = "今日新闻--日本发生大地震";
        redisTemplate.getConnectionFactory().getConnection().publish(topicName.getBytes(CharsetUtil.UTF_8), topicValue.getBytes(CharsetUtil.UTF_8));
        return "今日新闻---主题发布";
    }
    //http://localhost:8081/redis/setValue2
    @RequestMapping("/setValue2")
    public String setValue2() {
        String key = "今日新闻";
        String value = "日本发生大地震";
        redisTemplate.opsForValue().set(key, value);
        return key;
    }
//----------------------------------------------------------------------------------------

    //http://localhost:8080/redis/topicExpireListener 主题过期监听
    @RequestMapping("/topicExpireListener")
    public String topicExpireListener() {
        String topicName = "有效期今日新闻";
        messageListenerContainer.addMessageListener(redisExpireMsgListener, new ChannelTopic(topicName));
        return "有效期今日新闻---监听";
    }
    //http://localhost:8080/redis/topicExpirePublish  有效期主题发布
    @RequestMapping("/topicExpirePublish")
    public String topicExpirePublish() {
        String topicName = "有效期今日新闻";
        String topicValue = "有效期今日新闻---日本发生大地震";
        redisTemplate.expire(topicName, 10, TimeUnit.SECONDS);
        redisTemplate.getConnectionFactory().getConnection().publish(topicName.getBytes(CharsetUtil.UTF_8), topicValue.getBytes(CharsetUtil.UTF_8));
        return "有效期今日新闻---发布";
    }
    //http://localhost:8080/redis/setValue  主题过期监听
    @RequestMapping("/setValue")
    public String setValue() {
        String key = "有效期今日新闻";
        String value = "日本发生大地震";
        redisTemplate.opsForValue().set(key, value, 5, TimeUnit.SECONDS);
        return "有效期今日新闻---发布";
    }
//-----------------------------------------------
    @RequestMapping("/topicConsumer")
    public String topicConsume() {
        String topicName = "有效期今日新闻";
        redisTemplate.getConnectionFactory().getConnection().subscribe(redisMessageListener, topicName.getBytes());
        return "test";
    }


}
