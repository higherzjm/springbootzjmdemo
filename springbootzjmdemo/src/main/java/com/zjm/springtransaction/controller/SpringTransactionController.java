package com.zjm.springtransaction.controller;

import cn.hutool.core.bean.BeanUtil;
import com.zjm.springtransaction.DTO.LogInfoDTO;
import com.zjm.springtransaction.VO.LogInfoResultVO;
import com.zjm.springtransaction.entity.LogInfo;
import com.zjm.springtransaction.service.ISpringTransactionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zhujianming
 */
@RequestMapping("/springTransaction")
@RestController
@Slf4j
@Api(tags = "spring����")
public class SpringTransactionController {
    @Autowired
    private ISpringTransactionService springTransactionService;

    @PostMapping("/findSalaryPayrollOperateLogResult")
    @ApiOperation(value = "��ѯ��־", notes = "��ѯ��־")
    public List<LogInfoResultVO> findSalaryPayrollOperateLogResult(@RequestBody @Validated LogInfoDTO logInfoDTO) {
        return springTransactionService.findSalaryPayrollOperateLogResult(logInfoDTO);
    }

    @PostMapping("/saveSalaryPayrollOperateLogResult")
    @ApiOperation(value = "������־", notes = "������־")
    public String saveSalaryPayrollOperateLogResult(@RequestBody @Validated LogInfoDTO logInfoDTO) {
        LogInfo logInfo = new LogInfo();
        BeanUtil.copyProperties(logInfoDTO, logInfo);
        springTransactionService.saveSalaryPayrollOperateLogResult(logInfo, logInfoDTO.getActionNum());
        return "����ɹ�";
    }

    @GetMapping("/queryDynamicTableInfo/{id}")
    @ApiOperation(value = "mybatis��̬��ѯ", notes = "mybatis��̬��ѯ")
    public LogInfo queryDynamicTableInfo(@ApiParam(name = "id", value = "����id") @PathVariable("id") String id) {
        return springTransactionService.queryDynamicTableInfo(id, LogInfo.class);
    }
}
