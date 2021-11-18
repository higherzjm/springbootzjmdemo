package com.zjm.springtransaction.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 *  公共字段自动填充
 */
@Primary
@Component
public class MybatisMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {

        setFieldValByName("createUser", "创建人",metaObject);
        setFieldValByName("createTime", new Date(),metaObject);
        setFieldValByName("updateUser", "更新人",metaObject);
        setFieldValByName("updateTime", new Date(),metaObject);

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        //获取当前登录用户

        setFieldValByName("updateUser","更新人",metaObject);
        setFieldValByName("updateTime",new Date(),metaObject);
    }
}
