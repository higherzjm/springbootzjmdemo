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
@Api(tags = "�ֲ�ʽ��")
public class DistributedLockController {

    @GetMapping("/test1")
    @ApiOperation(value = "�ֲ�ʽ�����ò���1--������ʱ���Զ�ʧЧ")
    public String test1() {
        Jedis jedis = new Jedis("localhost", 6379);
        jedis.auth("123456");
        String lockKey = "test1LockKey";//��������־λ
        String lockValue = UUID.randomUUID().toString();//���ÿͻ���Ψһ��ʶ
        /**
         *private static final String XX = "xx";  ��ʾkey����ʱ��setֵ
         * private static final String NX = "nx";  ��ʾkey������ʱ��setֵ
         * private static final String PX = "px";  ��ʾ��ʾ����ʱ�䵥λ��΢��
         * private static final String EX = "ex"; ��ʾ��ʾ����ʱ�䵥λ����
         *
         * NX|XX, NX -- Only set the key if it does not already exist. XX -- Only set the
         * key if it already exist. EX|PX, expire time units: EX = seconds; PX = milliseconds
         **/
        log.info("lockValue:" + jedis.get(lockKey));
        SetParams setParams = new SetParams();
        setParams.nx();
        setParams.ex(15);//15���ɾ��
        String result = jedis.set(lockKey, lockValue, setParams);
        return getResponseResult(result);

    }

    @GetMapping("/test2")
    @ApiOperation(value = "�ֲ�ʽ�����ò���2--����������lua�ű��ֶ��ͷ�")
    public String test2() {
        Jedis jedis = new Jedis("localhost", 6379);
        jedis.auth("123456");
        String lockKey = "test2LockKey";//��������־λ
        String lockValue = UUID.randomUUID().toString();//���ÿͻ���Ψһ��ʶ
        String responseResult;
        try {
            log.info("lockValue:" + jedis.get(lockKey));
            SetParams setParams = new SetParams();
            setParams.nx();
            String result = jedis.set(lockKey, lockValue, setParams);
            responseResult = getResponseResult(result);

        } finally {
            /**
             *# ��ȡ KEYS[1] ��Ӧ�� Val
             * local cliVal = redis.call('get', KEYS[1])
             * # �ж� KEYS[1] �� ARGV[1] �Ƿ񱣳�һ��
             * if(cliVal == ARGV[1]) then
             *   # ɾ�� KEYS[1]
             *   redis.call('del', KEYS[1])
             *   return 'OK'
             * else
             *   return nil
             * end
             **/
            //ִ�����ͷ���  Lua �ű�
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
            log.info("��ȡ�����ɹ�");
            responseResult = "��ȡ�����ɹ� result:" + result;
        } else {
            log.info("��ȡ����ʧ�� result:" + result);
            responseResult = "��ȡ����ʧ�� result:" + result;
        }
        return responseResult;
    }
}
