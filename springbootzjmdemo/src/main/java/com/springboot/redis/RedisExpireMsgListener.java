package com.springboot.redis;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyspaceEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * @Description: 过期消息监听( 只监听频道名称，频道内容不监听)
 * @author 朱建明
 * @date 2021/3/7 14:53
 * @return
 */
@Component
public class RedisExpireMsgListener extends KeyspaceEventMessageListener {

    /**
     * Creates new {@link KeyspaceEventMessageListener}.
     *
     * @param listenerContainer must not be {@literal null}.
     */
    public RedisExpireMsgListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }


   /* @Override
    protected void doHandleMessage(Message message) {
        String channel = new String(message.getChannel());
        String topicValue = new String(message.getBody());

        System.out.println("频道名称:"+channel);
        System.out.println("主题名称:"+topicValue);
        if (channel.equals("__keyevent@0__:expired")) {
            System.out.println("----------------------------------");
            System.out.println("过期频道名称:"+channel);
            System.out.println("过期主题名称:"+topicValue);
        }
    }*/
    @Override
    public void onMessage(Message message, byte[] pattern) {
        String channel = new String(message.getChannel(), StandardCharsets.UTF_8);
        //过期的key
        String key = new String(message.getBody(),StandardCharsets.UTF_8);
        System.out.println("---------- 过期信息---"+new String(pattern)+":"+channel+":"+key);

    }

    @Override
    protected void doHandleMessage(Message message) {

    }


}