package com.zjm.springframework.mybatisInterceptor;

import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.plugin.*;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.util.*;

@Intercepts({@Signature(
        type = ParameterHandler.class,
        method = "setParameters",
        args = {PreparedStatement.class}
)})
@Service
public class SetParametersInterceptor implements Interceptor {
    private Properties properties = new Properties();
    public SetParametersInterceptor() {
    }

    public Object intercept(Invocation invocation) throws Throwable {
        if (!(invocation.getTarget() instanceof ParameterHandler)) {
            return invocation.proceed();
        } else {
            ParameterHandler parameterHandler = (ParameterHandler) invocation.getTarget();
            Object parameterObject = parameterHandler.getParameterObject();
            if (Objects.isNull(parameterObject)) {
                return invocation.proceed();
            } else {
                List<Object> encryptParams = new ArrayList();
                if (parameterObject instanceof MapperMethod.ParamMap) {
                    Map<String, Object> paramMap = (Map) parameterObject;
                    paramMap.keySet().forEach((key) -> {
                        if (key.startsWith("param")) {
                            Object param = paramMap.get(key);

                        }
                    });
                } else {
                    encryptParams.add(parameterObject);
                }

                for (int i = 0; i < encryptParams.size(); ++i) {
                    Object encryptParam = encryptParams.get(i);

                }

                return invocation.proceed();
            }
        }
    }

    public Object plugin(Object o) {
        return Plugin.wrap(o, this);
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}