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
@TableName("t_finance_salary_payroll_operate_log")
public class SalaryPayrollOperateLog extends Model<SalaryPayrollOperateLog> {

    private static final long serialVersionUID = 1L;


	/**
	 * 主键
	 */
   @TableId(value="id", type= IdType.UUID)
   //@DynamicQueryColumFlag(order = 1)
	private String id;
	/**
	 * 组织编码
	 */
	@TableField("organization_code")
	@DynamicQueryColumFlag(order =4)
	private String organizationCode;
	/**
	 * 复核记录ID
	 */
	@TableField("payroll_record_id")
	private String payrollRecordId;
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
	 * 员工编号
	 */
	@TableField("employee_no")
	private String employeeNo;
	/**
	 * 操作类型 说明： 
             1：HR-复核通过
             2：HR-取消复核
             3：HR-批量复核通过
             4：HR-Excel导出
             5：HR-复审通过
             6：HR-复审驳回
             7：HR-终审通过
             8：HR-终审驳回
             9：提交审批流
            10：下发薪资条
            11：申请付款
            12：更新数据
            13：财务-复核通过
            14：财务-取消复核
            15：财务-驳回
            16：财务-复审通过
            17：财务-复审驳回
            18：财务-Excel导出
            19：财务-批量复核通过
	 */
	@TableField("operate_type")
	private Integer operateType;
	/**
	 * 业务类型：1：HR复核 2：财务复核
	 */
	@TableField("busi_type")
	private Integer busiType;
	/**
	 * 业务单元 1 ：总览 2：详细
	 */
	@TableField("busi_unit")
	private Integer busiUnit;
	/**
	 * 操作对象 说明：
	 针对【复核通过】【取消复核】【驳回】【批量复核】【导出】等涉及到被操作对象的地方，存储员工姓名和员工编码
	 如：李晓（800658）多人用逗号分隔
	 */
	@TableField("operate_object")
	private String operateObject;
	/**
	 * 操作说明
	 */
	@TableField("content")
	private String content;
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
