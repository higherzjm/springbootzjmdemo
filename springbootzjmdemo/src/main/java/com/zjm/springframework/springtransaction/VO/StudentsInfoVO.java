package com.zjm.springframework.springtransaction.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 学生信息
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentsInfoVO {

    private static final long serialVersionUID = 1L;


	/**
	 * 主键
	 */
	private String id;
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 年龄
	 */
	private Integer age;
	/**
	 * 最后一次更新时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;
	/**
	 * 最后一次更新人
	 */
	private String updateUser;


}
