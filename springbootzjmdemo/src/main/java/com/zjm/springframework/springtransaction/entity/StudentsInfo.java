package com.zjm.springframework.springtransaction.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 日志信息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("student")
public class StudentsInfo extends Model<StudentsInfo> {

    private static final long serialVersionUID = 1L;


	/**
	 * 主键
	 */
   @TableId(value="id", type= IdType.UUID)
	private String id;
	/**
	 * 姓名
	 */
	@TableField("name")
	private String name;
	/**
	 * 年龄
	 */
	@TableField("age")
	private Integer age;

	/**
	 * 最后一次更新时间
	 */
	@TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
	private Date updateTime;
	/**
	 * 最后一次更新人
	 */
	@TableField(value = "update_user",fill = FieldFill.INSERT_UPDATE)
	private String updateUser;


}
