package com.zjm.springframework.mybatisInterceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Intercepts({@Signature(
        type = Executor.class,
        method = "update",
        args = {MappedStatement.class, Object.class}
)})
@Slf4j
@Service
public class MapperUpdateInterceptor implements Interceptor {
    private Properties properties = new Properties();

    public MapperUpdateInterceptor() {
    }

    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        if (SqlCommandType.INSERT.equals(sqlCommandType) || SqlCommandType.UPDATE.equals(sqlCommandType)) {
            Object parameter = invocation.getArgs()[1];
            List<Object> hashParams = new ArrayList();

            if (parameter instanceof MapperMethod.ParamMap) {
                Map<String, Object> paramMap = (Map) parameter;
            /*    paramMap.keySet().forEach((key) -> {
                    if (key.startsWith("param")) {
                        hashParams.add(paramMap.get(key));
                    }else {

                    }
                });*/
               paramMap.forEach((k,v)->{
                   if (k.equals("name")){
                       paramMap.put(k,v+":【mybatis更新拦截修改属性值，修改数据操作】");
                   }
               });
            } else {
                hashParams.add(parameter);
            }

            for (int i = 0; i < hashParams.size(); ++i) {
                Object hashParam = hashParams.get(i);
                if (hashParam==null){
                    continue;
                }
                Class clazz = hashParam.getClass();
                Field field = clazz.getDeclaredField("name");
                field.setAccessible(true);
                field.set(hashParam, "【mybatis更新拦截修改属性值，保存数据操作】：" + field.get(hashParam));
                log.info("hashParam:{}", hashParam);

            }
        }

        return invocation.proceed();
    }

    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}
