package com.zjm.springframework.mybatisInterceptor;

import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

@Intercepts({@Signature(
        type = ResultSetHandler.class,
        method = "handleResultSets",
        args = {Statement.class}
)})
/**
 * @description mybatis返回拦截，修改属性值
 * @date 2022/3/24 11:17
 */
@Service
public class HandleResultSetsInterceptor implements Interceptor {
    private static final Logger log = LoggerFactory.getLogger(HandleResultSetsInterceptor.class);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object ret = invocation.proceed();
        if (Objects.isNull(ret)) {
            return null;
        } else {
            if (ret instanceof List) {
                for (Object singRet : (List) ret) {
                    reflectValue(singRet);
                }
            } else {
                reflectValue(ret);
            }
            return ret;
        }
    }
    /**
     * @description 反射修改对象属性值
     * @date 2022/3/24 11:26
     */
    private void reflectValue(Object ret) {
        Class clazz = ret.getClass();
        Arrays.stream(clazz.getDeclaredFields()).forEach(f -> {
            try {
                //设置属性可访问
                f.setAccessible(true);
                log.info("f:{}", f.get(ret));
                if (f.getName().equals("name")) {
                    f.set(ret, "【mybatis返回拦截修改属性值，查询操作】:" + f.get(ret));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        log.info("p:{}", properties);
    }
}