package com.zjm.redis.base;

import io.netty.util.CharsetUtil;
import io.swagger.annotations.Api;
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
@Api(tags = "RedisӦ��")
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
    @GetMapping("/getValue")
    public String getValue() {
        String key = "��������";
        String value = redisTemplate.opsForValue().get(key);
        return value;
    }


//--------------------------------------------------------------------------------

    //http://localhost:8081/redis/topicListener  �������
    @GetMapping("/topicListener")
    public String topicListener() {
        String topicName = "��������";
        messageListenerContainer.addMessageListener(redisMessageListener, new ChannelTopic(topicName));
        return "��������--����";
    }

    //http://localhost:8081/redis/topicPublish  ���ⷢ��
    @GetMapping("/topicPublish")
    public String topicPublish() {
        String topicName = "��������";
        String topicValue = "��������--�ձ����������";
        redisTemplate.getConnectionFactory().getConnection().publish(topicName.getBytes(CharsetUtil.UTF_8), topicValue.getBytes(CharsetUtil.UTF_8));
        return "��������---���ⷢ��";
    }
    //http://localhost:8081/redis/setValue2
    @GetMapping("/setValue2")
    public String setValue2() {
        String key = "��������";
        String value = "�ձ����������";
        redisTemplate.opsForValue().set(key, value);
        return key;
    }
//----------------------------------------------------------------------------------------

    //http://localhost:8080/redis/topicExpireListener ������ڼ���
    @GetMapping("/topicExpireListener")
    public String topicExpireListener() {
        String topicName = "��Ч�ڽ�������";
        messageListenerContainer.addMessageListener(redisExpireMsgListener, new ChannelTopic(topicName));
        return "��Ч�ڽ�������---����";
    }
    //http://localhost:8080/redis/topicExpirePublish  ��Ч�����ⷢ��
    @GetMapping("/topicExpirePublish")
    public String topicExpirePublish() {
        String topicName = "��Ч�ڽ�������";
        String topicValue = "��Ч�ڽ�������---�ձ����������";
        redisTemplate.expire(topicName, 10, TimeUnit.SECONDS);
        redisTemplate.getConnectionFactory().getConnection().publish(topicName.getBytes(CharsetUtil.UTF_8), topicValue.getBytes(CharsetUtil.UTF_8));
        return "��Ч�ڽ�������---����";
    }
    //http://localhost:8080/redis/setValue  ������ڼ���
    @GetMapping("/setValue")
    public String setValue() {
        String key = "��Ч�ڽ�������";
        String value = "�ձ����������";
        redisTemplate.opsForValue().set(key, value, 5, TimeUnit.SECONDS);
        return "��Ч�ڽ�������---����";
    }
//-----------------------------------------------
    @GetMapping("/topicConsumer")
    public String topicConsume() {
        String topicName = "��Ч�ڽ�������";
        redisTemplate.getConnectionFactory().getConnection().subscribe(redisMessageListener, topicName.getBytes());
        return "test";
    }


}
