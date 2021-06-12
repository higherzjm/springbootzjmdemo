package com.zjm.springtransaction.service.impl;

import cn.hutool.core.util.ReflectUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.ImmutableMap;
import com.zjm.customannotation.DynamicQueryColumFlag;
import com.zjm.springtransaction.DTO.LogInfoDTO;
import com.zjm.springtransaction.VO.LogInfoResultVO;
import com.zjm.springtransaction.entity.LogInfo;
import com.zjm.springtransaction.mapper.LogInfoMapper;
import com.zjm.springtransaction.service.ISpringTransactionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    private static final String NAMESPACE_SELECT = "com.zjm.springtransaction.mapper.LogInfoMapper.dynamicSelectOne";
    @Resource
    private LogInfoMapper logInfoMapper;
    @Autowired
    protected SqlSession sqlSession;

    @Override
    public List<LogInfoResultVO> findSalaryPayrollOperateLogResult(LogInfoDTO logInfoDTO) {
        List<LogInfoResultVO> logInfoResultVOList = logInfoMapper.querySalaryPayrollOperateLog(logInfoDTO);
        return logInfoResultVOList;
    }

    public boolean checkExistSalaryPayrollOperateLogInMonth(String employeeCode, Integer month) {
        LambdaQueryWrapper<LogInfo> lambdaQueryWrapper = new LambdaQueryWrapper<LogInfo>().eq(LogInfo::getMonth, month)
                .eq(LogInfo::getEmployeeCode, employeeCode).select(LogInfo::getId,LogInfo::getYear);
        LogInfo loginfo = logInfoMapper.selectOne(lambdaQueryWrapper);
        return loginfo != null;
    }

    /**
     * mysql默认隔离级别为可重复读，同一接口同时请求多次读到的数据会一致，
     * 如下代码加事务请求两次会插入两条，不加事务就不会
     * propagation==Propagation.NOT_SUPPORTED   传播级别不支持事务 或 isolation= Isolation.READ_UNCOMMITTED 隔离级别为读未提交，这样只会拆入一条
     **/
    //@Transactional(propagation=Propagation.NOT_SUPPORTED,isolation= Isolation.READ_UNCOMMITTED)
    @Override
    public void saveSalaryPayrollOperateLogResult(LogInfo logInfo, String actionNum) {
        log.info("logInfo:" + logInfo);
        logInfo.setId(UUID.randomUUID().toString());
         //判断是否存在
        if (!checkExistSalaryPayrollOperateLogInMonth(logInfo.getEmployeeCode(), logInfo.getMonth())) {
            log.info("可插入");
            logInfoMapper.insert(logInfo);
        }else {
            log.info("已存在");
        }
        //测试高并发的时候使用
        if ("1".equals(actionNum)) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public <T> T queryDynamicTableInfo(String id, Class<T> dataClazz) {
        String tableName = dataClazz.getAnnotation(TableName.class).value();
        String tableColumns = getLogTableFields(dataClazz).stream().collect(Collectors.joining(","));
        String sql = buildSelectSql(tableName, tableColumns, id);
        return selectOne(sql, dataClazz);
    }
    /**
     * 数据查询
     *
     * @param sql
     * @return
     */
    protected <T> T selectOne(String sql, Class<T> resultClass) {
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
    protected Map<String, Object> selectOne(String sql) {
        return sqlSession.selectOne(NAMESPACE_SELECT, ImmutableMap.of("sql", sql));
    }
    /**
     * 获取实体需要记录的字段对应的库表字段
     *
     * @param dataClazz
     * @return
     */
    protected List<String> getLogTableFields(Class dataClazz) {
        List<Field> fieldList = getLogEntityFields(dataClazz);
        return fieldList.stream().filter(field -> !StringUtils.isEmpty(field.getAnnotation(TableField.class).value()))
                .map(field -> field.getAnnotation(TableField.class).value()).collect(Collectors.toList());
    }

    /**
     * 获取实体需要记录的字段
     *
     * @param dataClazz
     * @return
     */
    protected List<Field> getLogEntityFields(Class dataClazz) {
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
     *
     * @param tableName
     * @param fields
     * @param id
     * @return
     */
    private String buildSelectSql(String tableName, String fields, String id) {
        return String.format("select id, %s from %s where id = '%s'", fields, tableName, id);
    }
}
