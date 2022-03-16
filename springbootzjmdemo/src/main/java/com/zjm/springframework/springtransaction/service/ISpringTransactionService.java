package com.zjm.springframework.springtransaction.service;

import com.zjm.springframework.springtransaction.DTO.LogInfoDTO;
import com.zjm.springframework.springtransaction.VO.LogInfoResultVO;
import com.zjm.springframework.springtransaction.entity.LogInfo;

import java.util.List;

/**
 * @author zhujianming
 */
public interface ISpringTransactionService {
    List<LogInfoResultVO> findLog(LogInfoDTO logInfoDTO);
    void  saveLog(LogInfo loginfo, String actionNum) ;
    void subLogSave(LogInfo logInfo);
    <T> T queryDynamicTableInfo(String id, Class<T> dataClazz);
}
