package com.zjm.redis.distributedlock;

import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping("/distributedLock")
@RestController
@Slf4j
@Api(tags = "分布式锁")
public class DistributedLockController {

    @GetMapping("/test1")
    @ApiOperation(value = "分布式锁设置测试1--按过期时间自动失效")
    public String test1() {
        Jedis jedis = new Jedis("localhost", 6379);
        jedis.auth("123456");
        String lockKey = "test1LockKey";//设置锁标志位
        String lockValue = UUID.randomUUID().toString();//设置客户端唯一标识
        /**
         *private static final String XX = "xx";  表示key存在时才set值
         * private static final String NX = "nx";  表示key不存在时才set值
         * private static final String PX = "px";  表示表示过期时间单位是微秒
         * private static final String EX = "ex"; 表示表示过期时间单位是秒
         *
         * NX|XX, NX -- Only set the key if it does not already exist. XX -- Only set the
         * key if it already exist. EX|PX, expire time units: EX = seconds; PX = milliseconds
         **/
        log.info("lockValue:" + jedis.get(lockKey));
        SetParams setParams = new SetParams();
        setParams.nx();
        setParams.ex(15);//15秒后删除
        String result = jedis.set(lockKey, lockValue, setParams);
        return getResponseResult(result);

    }

    @GetMapping("/test2")
    @ApiOperation(value = "分布式锁设置测试2--处理完任务lua脚本手动释放")
    public String test2() {
        Jedis jedis = new Jedis("localhost", 6379);
        jedis.auth("123456");
        String lockKey = "test2LockKey";//设置锁标志位
        String lockValue = UUID.randomUUID().toString();//设置客户端唯一标识
        String responseResult;
        try {
            log.info("lockValue:" + jedis.get(lockKey));
            SetParams setParams = new SetParams();
            setParams.nx();
            String result = jedis.set(lockKey, lockValue, setParams);
            responseResult = getResponseResult(result);

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
        return responseResult;
    }

    public String getResponseResult(String result) {
        String responseResult;
        if ("OK".equals(result)) {
            log.info("获取到锁成功");
            responseResult = "获取到锁成功 result:" + result;
        } else {
            log.info("获取到锁失败 result:" + result);
            responseResult = "获取到锁失败 result:" + result;
        }
        return responseResult;
    }
}
