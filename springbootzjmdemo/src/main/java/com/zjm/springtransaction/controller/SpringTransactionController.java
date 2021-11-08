package com.zjm.springtransaction.controller;

import cn.hutool.core.bean.BeanUtil;
import com.zjm.springtransaction.DTO.LogInfoDTO;
import com.zjm.springtransaction.VO.LogInfoResultVO;
import com.zjm.springtransaction.entity.LogInfo;
import com.zjm.springtransaction.entity.StudentsInfo;
import com.zjm.springtransaction.service.ISpringTransactionService;
import com.zjm.springtransaction.service.IStudentService;
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
@Api(tags = "spring事务")
public class SpringTransactionController {
    @Autowired
    private ISpringTransactionService springTransactionService;
    @Autowired
    private IStudentService studentService;
    @PostMapping("/queryStudents/{name}")
    @ApiOperation(value = "查询学生列表", notes = "query student list")
    public List<StudentsInfo> queryStudentList(@ApiParam(name = "name", value = "姓名",defaultValue = "张") @PathVariable("name") String name) {
        return studentService.queryStudentList(name);
    }
    @PostMapping("/updateIdentityUnTransaction/{id}/{value}")
    @ApiOperation(value = "更新学生身份_不带事务")
    public String updateIdentityUnTransaction(@ApiParam(name = "id", value = "主键",defaultValue = "1") @PathVariable("id") String id, @ApiParam(name = "value", value = "更新值")@PathVariable("value") String value) {
        return studentService.updateIdentityUnTransaction(id,value);
    }
    @PostMapping("/updateIdentityTransaction/{id}/{value}")
    @ApiOperation(value = "更新学生身份_带事务")
    public String updateIdentityTransaction(@ApiParam(name = "id", value = "主键",defaultValue = "1") @PathVariable("id") String id, @ApiParam(name = "value", value = "更新值")@PathVariable("value") String value) {
        return studentService.updateIdentityTransaction(id,value);
    }
    @PostMapping("/findSalaryPayrollOperateLogResult")
    @ApiOperation(value = "查询日志", notes = "查询日志")
    public List<LogInfoResultVO> findSalaryPayrollOperateLogResult(@RequestBody @Validated LogInfoDTO logInfoDTO) {
        return springTransactionService.findSalaryPayrollOperateLogResult(logInfoDTO);
    }

    @PostMapping("/saveSalaryPayrollOperateLogResult")
    @ApiOperation(value = "保存日志", notes = "保存日志")
    public String saveSalaryPayrollOperateLogResult(@RequestBody @Validated LogInfoDTO logInfoDTO) {
        LogInfo logInfo = new LogInfo();
        BeanUtil.copyProperties(logInfoDTO, logInfo);
        springTransactionService.saveSalaryPayrollOperateLogResult(logInfo, logInfoDTO.getActionNum());
        return "保存成功";
    }

    @GetMapping("/queryDynamicTableInfo/{id}")
    @ApiOperation(value = "mybatis动态查询", notes = "mybatis动态查询")
    public LogInfo queryDynamicTableInfo(@ApiParam(name = "id", value = "主键id") @PathVariable("id") String id) {
        return springTransactionService.queryDynamicTableInfo(id, LogInfo.class);
    }
}
