package com.zjm.springframework.springtransaction.service.impl;

import cn.hutool.core.util.ReflectUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.ImmutableMap;
import com.zjm.customannotation.DynamicQueryColumFlag;
import com.zjm.springframework.springtransaction.DTO.LogInfoDTO;
import com.zjm.springframework.springtransaction.VO.LogInfoResultVO;
import com.zjm.springframework.springtransaction.entity.LogInfo;
import com.zjm.springframework.springtransaction.mapper.LogInfoMapper;
import com.zjm.springframework.springtransaction.service.ISpringTransactionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhujianming
 */
@Service
@Slf4j
public class SpringTransactionServiceImpl implements ISpringTransactionService {
    private static final String NAMESPACE_SELECT = "com.zjm.springframework.springtransaction.mapper.LogInfoMapper.dynamicSelectOne";
    @Resource
    private LogInfoMapper logInfoMapper;
    @Autowired
    protected SqlSession sqlSession;

    @Override
    public List<LogInfoResultVO> findLog(LogInfoDTO logInfoDTO) {
        return logInfoMapper.querySalaryPayrollOperateLog(logInfoDTO);
    }

    private boolean checkLog(String employeeCode, Integer month) {
        LambdaQueryWrapper<LogInfo> lambdaQueryWrapper = new LambdaQueryWrapper<LogInfo>().eq(LogInfo::getMonth, month)
                .eq(LogInfo::getEmployeeCode, employeeCode).select(LogInfo::getId,LogInfo::getYear);
        LogInfo loginfo = logInfoMapper.selectOne(lambdaQueryWrapper);
        return loginfo != null;
    }

    /**
     * mysql默认隔离级别为可重复读，同一接口同时请求多次读到的数据会一致，
     * 并发情况下如下代码加事务请求多次会添加多条，不加事务就不会
     * propagation==Propagation.NOT_SUPPORTED   传播级别不支持事务 或 isolation= Isolation.READ_UNCOMMITTED 隔离级别为读未提交，这样只会拆入一条
     **/
    //@Transactional(propagation= Propagation.NOT_SUPPORTED,isolation= Isolation.READ_UNCOMMITTED)
    //@Transactional(isolation= Isolation.READ_COMMITTED)
    //@Transactional(isolation= Isolation.READ_UNCOMMITTED)
    //@Transactional
    @Override
    public void saveLog(LogInfo logInfo, String actionNum){
        log.info("logInfo:" + logInfo);
        //判断是否存在
       /* if (!checkLog(logInfo.getEmployeeCode(), logInfo.getMonth())) {
            log.info("可插入");
        }else {
            logInfoMapper.delete(new LambdaQueryWrapper<LogInfo>().eq(LogInfo::getEmployeeCode,logInfo.getEmployeeCode()).eq(LogInfo::getMonth,logInfo.getMonth()));
        }
        logInfoMapper.insert(logInfo);
        //测试高并发的时候使用
        if ("1".equals(actionNum)) {
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/

        //------------测试存在事务，抛异常是否会回滚
        /**
         * @description  入库操作后业务产生异常，没有添加事务【@Transactional】不会回滚，有添加事务会回滚
         * @date 2022/3/16 13:40
         */
       /* logInfoMapper.insert(logInfo);
        try {
            int i=1/0;
        }catch (Exception e){
            //异常手动回滚，如果不设置回滚异常类型spring不会对所有异常进行回滚，可进行手动异常回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }*/


        //-----------测试跨方法存在事务是否有效
        /**
         * @description 事务无效
         * @date 2022/3/16 13:54
         */
        saveLog2(logInfo);
    }

    @Transactional
    public void saveLog2(LogInfo logInfo){
        logInfoMapper.insert(logInfo);
        int i=1/0;
    }

    @Override
    public <T> T queryDynamicTableInfo(String id, Class<T> dataClazz) {
        String tableName = dataClazz.getAnnotation(TableName.class).value();
        String tableColumns = String.join(",", getLogTableFields(dataClazz));
        String sql = buildSelectSql(tableName, tableColumns, id);
        return selectOne(sql, dataClazz);
    }
    /**
     * 数据查询
     */
    private <T> T selectOne(String sql, Class<T> resultClass) {
        Map<String, Object> result = selectOne(sql);
        if (result != null) {
            Map<String, Object> tempMap = new HashMap<>();
            result.forEach((key, value) -> tempMap.put(com.baomidou.mybatisplus.core.toolkit.StringUtils.underlineToCamel(key), value));
            try {
                //注意不能使用BeanUtil.toBean， 日期类型Date跟LocalDateTime无法转化
                T resultObject = resultClass.newInstance();
                tempMap.forEach((key, value) -> ReflectUtil.setFieldValue(resultObject, key, value));
                return resultObject;
            } catch (Exception e) {
            }
        }
        return null;
    }
    /**
     * 查询一条数据
     *
     * @param sql
     * @return
     */
    private Map<String, Object> selectOne(String sql) {
        return sqlSession.selectOne(NAMESPACE_SELECT, ImmutableMap.of("sql", sql));
    }
    /**
     * 获取实体需要记录的字段对应的库表字段
     */
    private List<String> getLogTableFields(Class dataClazz) {
        List<Field> fieldList = getLogEntityFields(dataClazz);
        return fieldList.stream().filter(field -> !StringUtils.isEmpty(field.getAnnotation(TableField.class).value()))
                .map(field -> field.getAnnotation(TableField.class).value()).collect(Collectors.toList());
    }

    /**
     * 获取实体需要记录的字段
     */
    private List<Field> getLogEntityFields(Class dataClazz) {
        return Arrays.stream(dataClazz.getDeclaredFields()).filter(field -> field.getAnnotation(DynamicQueryColumFlag.class) != null)
                .sorted((o1, o2) -> {
                    int order1 = o1.getAnnotation(DynamicQueryColumFlag.class).order();
                    int order2 = o2.getAnnotation(DynamicQueryColumFlag.class).order();
                    return order1 - order2;
                })
                .collect(Collectors.toList());
    }

    /**
     * 构建查询语句
     */
    private String buildSelectSql(String tableName, String fields, String id) {
        return String.format("select id, %s from %s where id = '%s'", fields, tableName, id);
    }
}
