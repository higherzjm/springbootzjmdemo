package com.zjm.base.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@ApiModel(description = "学校vo")
public  class SchoolVO {
    //教师人数
    @ApiModelProperty(value = "教师人数")
    private Integer teacherNum;
    //学生人数
    @ApiModelProperty(value = "学生人数")
    private Integer studentNum;
    //学校名称
    @ApiModelProperty(value = "学校名称")
    private String name;
    //子级school
    @ApiModelProperty(value = "子级school")
    private List<SchoolVO> subSchoolVOList;

}