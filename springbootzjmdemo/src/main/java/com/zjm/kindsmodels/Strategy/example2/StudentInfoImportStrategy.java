package com.zjm.kindsmodels.Strategy.example2;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author zhujianming
 * ѧ����Ϣ�������
 */
@Slf4j
public class StudentInfoImportStrategy extends  AbstactFileImportStrategy {
    @Override
    public boolean checkDatas(List<String> dataList) {
        log.info("ѧ����Ϣ����У��");
        return true;
    }

    @Override
    public void saveDatas(List<String> dataList) {
        log.info("ѧ����Ϣ���");
    }
}
