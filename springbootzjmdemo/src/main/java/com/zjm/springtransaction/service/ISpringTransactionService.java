package com.zjm.springtransaction.service;

import com.zjm.springtransaction.DTO.LogInfoDTO;
import com.zjm.springtransaction.VO.LogInfoResultVO;
import com.zjm.springtransaction.entity.LogInfo;

import java.util.List;

/**
 * @author zhujianming
 */
public interface ISpringTransactionService {
    List<LogInfoResultVO> findSalaryPayrollOperateLogResult(LogInfoDTO logInfoDTO);
    void  saveSalaryPayrollOperateLogResult(LogInfo loginfo, String actionNum);
    <T> T queryDynamicTableInfo(String id, Class<T> dataClazz);
}
