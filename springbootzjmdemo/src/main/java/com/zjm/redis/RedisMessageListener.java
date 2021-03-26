package com.zjm.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

//消息监听器
@Component
public class RedisMessageListener implements MessageListener {
	@Autowired
	public StringRedisTemplate redisTemplate;

	@Override
	public void onMessage(Message message, byte[] pattern) {
		String topicName = new String(message.getChannel());
		String topicValue = new String(message.getBody());
		System.out.println("主题名称:"+topicName);
		System.out.println("主题内容:"+topicValue);
		//设置过期时间
	}

}