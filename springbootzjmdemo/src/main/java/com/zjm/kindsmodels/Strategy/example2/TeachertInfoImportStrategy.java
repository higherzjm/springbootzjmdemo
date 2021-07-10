package com.zjm.kindsmodels.Strategy.example2;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author zhujianming
 * ��ʦ��Ϣ�������
 */
@Slf4j
public class TeachertInfoImportStrategy extends AbstactFileImportStrategy {
    @Override
    public boolean checkDatas(List<String> dataList) {
        log.info("��ʦ��Ϣ����У��");
        return true;
    }

    @Override
    public void saveDatas(List<String> dataList) {
        log.info("��ʦ��Ϣ���");
    }
}
