package com.zjm.baseapplication.service;

import com.zjm.baseapplication.VO.SchoolVO;

/**
 * @author zhujianming
 */
public interface ITreeSortStatisticsService {
     SchoolVO tressStatisticsByOverall(SchoolVO schoolVO);

    SchoolVO treeStatisticsBySign(SchoolVO schoolVO);
}
