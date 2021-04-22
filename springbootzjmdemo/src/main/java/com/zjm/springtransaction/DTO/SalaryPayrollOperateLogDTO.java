package com.zjm.springtransaction.DTO;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigInteger;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 薪资复核-复核操作日志表
 说明：
 针对复核模块的复核动作进行单独的日志记录
 * </p>
 *
 * @author liangchangcheng
 * @since 2020-12-03
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalaryPayrollOperateLogDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "组织机构编码",example = "ORG1343456435840925697")
    private String organizationCode;

    @ApiModelProperty(value = "计薪年", required = true)
    private Integer year;

    @ApiModelProperty(value = "计薪月", required = true)
    private Integer month;

    @ApiModelProperty(value = "操作时间-开始")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate operateDateBegin;

    @ApiModelProperty(value = "操作时间-结束")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate operateDateEnd;


    @ApiModelProperty(value = "操作类型(无需前端传值)")
    private List<Integer> operateTypeList;





}
