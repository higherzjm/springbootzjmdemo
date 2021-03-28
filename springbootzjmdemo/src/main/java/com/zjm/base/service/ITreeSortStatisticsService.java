package com.zjm.base.service;

import com.zjm.base.VO.SchoolVO;

import java.util.List;

/**
 * @author zhujianming
 */
public interface ITreeSortStatisticsService {
     SchoolVO tressStatisticsByOverall(SchoolVO schoolVO);

    SchoolVO treeStatisticsBySign(SchoolVO schoolVO);
}
