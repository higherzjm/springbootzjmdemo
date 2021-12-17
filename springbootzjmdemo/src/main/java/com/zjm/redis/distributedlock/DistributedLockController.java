package com.zjm.redis.distributedlock;

import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.params.SetParams;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


/**
 * @author zhujianming
 */
@RequestMapping("/distributedLock")
@RestController
@Slf4j
@Api(tags = "Redis")
public class DistributedLockController {

    @GetMapping("/test1")
    @ApiOperation(value = "分布式锁--按过期时间自动失效")
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
    @ApiOperation(value = "分布式锁--处理完任务lua脚本手动释放")
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

    private String getResponseResult(String result) {
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

    @GetMapping("/redissonLockUnitInv")
    @ApiOperation(value = "分布式锁-redisson分布式锁【独立Redis环境】")
    public String redissonLockUnitInv() {
        Config config = new Config();
        SingleServerConfig singleServerConfig = config.useSingleServer();
        singleServerConfig.setAddress("redis://127.0.0.1:6379");//redis ip端口不能错
        RedissonClient redissonClient = Redisson.create(config);
        String unitInvLockName = "redissonLockUnitInv";
        log.info("锁id:" + unitInvLockName);
        RLock rLock = redissonClient.getLock(unitInvLockName);
        String ret;
        try {
            //只要有锁住，不管多长时间，只要后面的业务执行完就会释放
            rLock.lock(1, TimeUnit.MILLISECONDS);
            log.info("业务正在处理");
            Thread.sleep(5000);
            ret = "redisson分布式锁:上锁成功";
        } catch (Exception e) {
            ret = "redisson分布式锁:上锁失败:" + e.getMessage();
        } finally {
            log.info("任务执行完毕，锁释放");
            rLock.unlock();
        }
        return ret;
    }

    @Resource
    private RedissonClient redissonClient;

    @GetMapping("/redissonLockSpringInt")
    @ApiOperation(value = "分布式锁-redisson分布式锁【spring集成】")
    public String redissonLockSpringInt() {
        for (int i = 0; i < 7; i++) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Thread thread = new Thread(new ConcurrenceThread("任务" + i));
            thread.start();
        }
        return "并发测试";
    }

    /**
     * 并发测试(无法续期，到了时间就会释放锁)
     */
    public void concurrenceTest(String targetName) {
        String SpringIntLockName = "myLockSpringInt";
        log.info("【{}】进入抢资源", targetName);
        RLock rLock = redissonClient.getLock(SpringIntLockName);
        try {
            //30毫秒后释放锁，没有续期
            rLock.lock(30, TimeUnit.MILLISECONDS);

            log.info("【{}】业务正在处理:" + rLock.isLocked(), targetName);
            Thread.sleep(10000);
            log.info("【{}】执行完毕，是否还是锁住状态:" + rLock.isLocked() + ",是否被当前线程锁住:" + rLock.isHeldByCurrentThread(), targetName);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            log.info("【{}】进入解锁阶段", targetName);
            if (rLock.isLocked() && rLock.isHeldByCurrentThread()) {
                rLock.unlock();
            }
            log.info("【{}】解锁成功，锁释放", targetName);
        }
    }

    /**
     * 并发测试(锁自动续期，每隔10秒续期一次)
     */
    public void concurrenceTest2(String targetName) {
        String SpringIntLockName = "myLockSpringInt2";
        log.info("【{}】进入抢资源", targetName);
        RLock rLock = redissonClient.getLock(SpringIntLockName);
        boolean isGet = false;
        try {
            //2.如果锁已经存在，则尝试12秒获取锁，12秒内仍未获取锁则返回false【每隔10秒续期一次】
            isGet = rLock.tryLock(12000, TimeUnit.MILLISECONDS);
            log.info("{}是否获取锁成功:{}", targetName, isGet);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (isGet) {
            try {
                log.info("【{}】业务正在处理:" + rLock.isLocked(), targetName);
                Thread.sleep(100000);
                log.info("【{}】执行完毕，是否还是锁住状态:" + rLock.isLocked() + ",是否被当前线程锁住:" + rLock.isHeldByCurrentThread(), targetName);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                log.info("【{}】进入解锁阶段", targetName);
                if (rLock.isLocked() && rLock.isHeldByCurrentThread()) {
                    rLock.unlock();
                }
                log.info("【{}】解锁成功，锁释放", targetName);
            }

        }
    }

    /**
     * 并发线程
     */
    class ConcurrenceThread implements Runnable {
        private String targetName;

        public ConcurrenceThread(String targetName) {
            this.targetName = targetName;
        }

        @Override
        public void run() {
            concurrenceTest2(targetName);
        }
    }
}
