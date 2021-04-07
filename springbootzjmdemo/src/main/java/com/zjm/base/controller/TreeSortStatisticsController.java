package com.zjm.base.controller;

import com.zjm.base.VO.SchoolVO;
import com.zjm.base.service.ITreeSortStatisticsService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @author zhujianming
 */
@RequestMapping("/treeOperation")
@RestController
@Api(tags = "结构树应用")
public class TreeSortStatisticsController {
    @Autowired
    private ITreeSortStatisticsService treeSortStatisticsService;
    private SchoolVO v0;

    public TreeSortStatisticsController() {
        initOrResetTreeInfo();
    }

    public void initOrResetTreeInfo() {
        v0 = SchoolVO.builder().name("v0").teacherNum(1).studentNum(2).build();
        SchoolVO v1 = SchoolVO.builder().name("v1").teacherNum(1).studentNum(2).build();
        SchoolVO v2 = SchoolVO.builder().name("v2").teacherNum(1).studentNum(2).build();
        List<SchoolVO> SchoolVOList = Arrays.asList(v1, v2);
        v0.setSubSchoolVOList(SchoolVOList);

        SchoolVO v3 = SchoolVO.builder().name("v3").teacherNum(1).studentNum(2).build();
        SchoolVO v4 = SchoolVO.builder().name("v4").teacherNum(1).studentNum(2).build();
        SchoolVOList = Arrays.asList(v3, v4);
        v1.setSubSchoolVOList(SchoolVOList);
        SchoolVO v5 = SchoolVO.builder().name("v5").teacherNum(1).studentNum(2).build();
        SchoolVO v6 = SchoolVO.builder().name("v6").teacherNum(1).studentNum(2).build();
        SchoolVOList = Arrays.asList(v5, v6);
        v2.setSubSchoolVOList(SchoolVOList);
        SchoolVO v7 = SchoolVO.builder().name("v7").teacherNum(1).studentNum(2).build();
        SchoolVO v8 = SchoolVO.builder().name("v8").teacherNum(1).studentNum(2).build();
        SchoolVOList = Arrays.asList(v7, v8);
        v3.setSubSchoolVOList(SchoolVOList);
        SchoolVO v9 = SchoolVO.builder().name("v9").teacherNum(1).studentNum(2).build();
        SchoolVO v10 = SchoolVO.builder().name("v10").teacherNum(1).studentNum(2).build();
        SchoolVO v11 = SchoolVO.builder().name("v11").teacherNum(1).studentNum(2).build();
        SchoolVO v12 = SchoolVO.builder().name("v12").teacherNum(1).studentNum(2).build();
        SchoolVOList = Arrays.asList(v9, v10, v11, v12);
        v5.setSubSchoolVOList(SchoolVOList);
        SchoolVO v13 = SchoolVO.builder().name("v13").teacherNum(1).studentNum(2).build();
        SchoolVO v14 = SchoolVO.builder().name("v14").teacherNum(1).studentNum(2).build();
        SchoolVOList = Arrays.asList(v13, v14);
        v11.setSubSchoolVOList(SchoolVOList);
        System.out.println(v0);
    }
    @RequestMapping("/initTreeInfo")
    public SchoolVO initTreeInfo() {
        this.initOrResetTreeInfo();
        return v0;
    }
    @RequestMapping("/treeStatisticsBySign")
    public SchoolVO treeStatisticsBySign() {
        this.initOrResetTreeInfo();

        //单一字段逐级统计
        SchoolVO schoolVO = treeSortStatisticsService.treeStatisticsBySign(v0);
        return schoolVO;
    }

    @RequestMapping("/tressStatisticsByOverall")
    public SchoolVO tressStatisticsByOverall() {
        this.initOrResetTreeInfo();

        //多字段同时逐级统计
        SchoolVO schoolVO = treeSortStatisticsService.tressStatisticsByOverall(v0);
        return schoolVO;
    }
}
