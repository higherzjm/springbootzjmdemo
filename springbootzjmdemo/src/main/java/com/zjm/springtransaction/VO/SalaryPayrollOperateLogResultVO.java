package com.zjm.springtransaction.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

/**
 * @author zhujianming
 */
@Data
public class SalaryPayrollOperateLogResultVO {

    @ApiModelProperty(value = "操作日志id")
    private String operateLogId;
    @ApiModelProperty(value = "操作时间：YYYY-MM-dd HH24:mm:ss")
    private String operateDateTime;
    @ApiModelProperty(value = "操作人:员工姓名(员工编号)")
    private String operateUser;
    @ApiModelProperty(value = "操作类型code:操作类型根据业务类型区分")
    private Integer operateType;
    @ApiModelProperty(value = "页面类型code:1 ：总览 2：详细")
    private Integer busiUnit;
    @ApiModelProperty("业务类型：1：HR复核 2：财务复核")
    private Integer busiType;
    @ApiModelProperty(value = "薪资年")
    private Integer year;
    @ApiModelProperty(value = "薪资月")
    private Integer month;
    @ApiModelProperty(value = "操作对象：显示单笔操作的操作对象，格式：员工姓名(员工编号)")
    private String operateObject;
    @ApiModelProperty(value = "操作内容描述")
    private String content;
    @ApiModelProperty(value = "页面类型名称:总览,详细")
    private String busiUnitName;
    @ApiModelProperty(value = "业务类型名称,根据用户在哪个页签操作系统带出")
    private String busiTypeName;
    @ApiModelProperty(value = "操作类型名称:细分HR复核页面和财务复核页面的操作类型")
    private String operateTypeName;
    @ApiModelProperty(value = "薪资年月:YYYY-MM")
    private String salaryPayrollYearMonth;


    @ApiModelProperty(value = "HR复核操作类型列表")
    private Map<Integer, Object> hrOperateTypeMap;
    @ApiModelProperty(value = "财务复核操作类型列表")
    private Map<Integer, Object> financeOperateTypeMap;
    @ApiModelProperty(value = "页面类型列表")
    private Map<Integer, Object> busiUnitMap;
    @ApiModelProperty(value = "业务类型列表")
    private Map<Integer, Object> busiTypeMap;


}
