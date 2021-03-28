package com.zjm.base.service;

import com.zjm.base.VO.SchoolVO;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhujianming
 */
@Service
public class TreeSortStatisticsServiceImpl implements ITreeSortStatisticsService {


    @Override
    public SchoolVO treeStatisticsBySign(SchoolVO schoolVO) {
        statisticslBySign(schoolVO);
        return schoolVO;
    }
    //单独字段汇总
    int statisticslBySign(SchoolVO SchoolVO) {
        List<SchoolVO> SchoolVOList = SchoolVO.getSubSchoolVOList();
        if (SchoolVOList == null) {
            return SchoolVO.getTeacherNum();
        }
        int sum = SchoolVO.getTeacherNum();
        for (SchoolVO SchoolVO1 : SchoolVOList) {
            sum = sum + statisticslBySign(SchoolVO1);
        }
        SchoolVO.setTeacherNum(sum);
        return sum;
    }
     //联合字段汇总
    @Override
    public SchoolVO tressStatisticsByOverall(SchoolVO schoolVO) {
        statisticsByOverall(schoolVO);
        return schoolVO;
    }

    //多字段同时逐级累加
    StatisticsVO statisticsByOverall(SchoolVO schoolVO) {
        List<SchoolVO> SchoolVOList = schoolVO.getSubSchoolVOList();
        if (SchoolVOList == null) {
            StatisticsVO statisticsVO = new StatisticsVO();
            statisticsVO.setStudentNum(schoolVO.getStudentNum() == null ? 0 : schoolVO.getStudentNum());
            statisticsVO.setTeacherNum(schoolVO.getTeacherNum() == null ? 0 : schoolVO.getTeacherNum());
            return statisticsVO;
        }
        Integer totalStudentNum = schoolVO.getStudentNum() == null ? 0 : schoolVO.getStudentNum();
        Integer totalTeacherNum = schoolVO.getTeacherNum() == null ? 0 : schoolVO.getTeacherNum();
        for (SchoolVO SchoolVO1 : SchoolVOList) {
            StatisticsVO statisticsVO = statisticsByOverall(SchoolVO1);
            totalStudentNum = totalStudentNum + statisticsVO.getStudentNum();
            totalTeacherNum = totalTeacherNum + statisticsVO.getTeacherNum();
        }
        schoolVO.setTeacherNum(totalTeacherNum);
        schoolVO.setStudentNum(totalStudentNum);
        StatisticsVO statisticsVO = new StatisticsVO();
        statisticsVO.setTeacherNum(totalTeacherNum);
        statisticsVO.setStudentNum(totalStudentNum);
        return statisticsVO;
    }

    //需要累加的属性
    @Data
    class StatisticsVO {
        private Integer teacherNum;
        private Integer studentNum;

    }
}
