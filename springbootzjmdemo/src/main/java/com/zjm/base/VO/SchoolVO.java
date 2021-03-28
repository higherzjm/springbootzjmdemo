package com.zjm.base.VO;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public  class SchoolVO {
    //教师人数
    private Integer teacherNum;
    //学生人数
    private Integer studentNum;
    //学校名称
    private String name;
    //子级school
    private List<SchoolVO> subSchoolVOList;

}