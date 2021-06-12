package com.zjm.springtransaction.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.zjm.customannotation.DynamicQueryColumFlag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 日志信息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("log_info")
public class LogInfo extends Model<LogInfo> {

    private static final long serialVersionUID = 1L;


	/**
	 * 主键
	 */
   @TableId(value="id", type= IdType.UUID)
	private String id;
	/**
	 * 员工编码
	 */
	@TableField("employee_code")
	private String employeeCode;
	/**
	 * 员工名称
	 */
	@TableField("employee_name")
	private String employeeName;
	/**
	 * 年份 格式YYYY
	 */
	@TableField("year")
	@DynamicQueryColumFlag(order = 2)
	private Integer year;
	/**
	 * 月份 
	 */
	@TableField("month")
	@DynamicQueryColumFlag(order = 3)
	private Integer month;
	/**
	 * 创建时间
	 */
	@TableField(value = "create_time",fill = FieldFill.INSERT)
	private LocalDateTime createTime;
	/**
	 * 创建人
	 */
	@TableField(value = "create_user",fill = FieldFill.INSERT)
	private String createUser;
	/**
	 * 最后一次更新时间
	 */
	@TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
	private LocalDateTime updateTime;
	/**
	 * 最后一次更新人
	 */
	@TableField(value = "update_user",fill = FieldFill.INSERT_UPDATE)
	private String updateUser;




}
