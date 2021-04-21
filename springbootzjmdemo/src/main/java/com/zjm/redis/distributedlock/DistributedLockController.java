package com.zjm.redis.distributedlock;

import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.params.SetParams;

import java.util.UUID;


/**
 * @author zhujianming
 */
@RequestMapping("/distributedLockController")
@RestController
@Slf4j
@Api(tags = "分布式锁")
public class DistributedLockController {
    @Test
    public void test1() {
        Jedis jedis = new Jedis("localhost", 6379);
        jedis.auth("123456");
        String lockKey = "lockKey";//设置锁标志位
        String lockValue = UUID.randomUUID().toString();//设置客户端唯一标识
        try {
            SetParams setParams = new SetParams();
            String result = jedis.set(lockKey, lockValue, setParams);
            if (!result.equals("OK")) {
                //未获取到锁失败
            }
        } finally {
            /**
             *# 获取 KEYS[1] 对应的 Val
             * local cliVal = redis.call('get', KEYS[1])
             * # 判断 KEYS[1] 与 ARGV[1] 是否保持一致
             * if(cliVal == ARGV[1]) then
             *   # 删除 KEYS[1]
             *   redis.call('del', KEYS[1])
             *   return 'OK'
             * else
             *   return nil
             * end
             **/
            //执行完释放锁  Lua 脚本
            String script = "local cliVal = redis.call('get', KEYS[1]) " +
                    "if(cliVal == ARGV[1]) then " +
                    "  redis.call('del', KEYS[1])" +
                    "  return 'OK' " +
                    "else" +
                    "  return nil " +
                    "end";
            jedis.eval(script, Lists.newArrayList(lockKey), Lists.newArrayList(lockValue));
        }
    }
}
