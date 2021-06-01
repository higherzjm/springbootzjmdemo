package com.zjm.kindsmodels.Strategy.example2;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

/*
 *抽象文件导入策略
 */
@Slf4j
public abstract class AbstactFileImportStrategy {
	public void importFIle(String filePath){
		log.info("文件路径:"+filePath);
		boolean checkFlag=this.checkDatas(null);
		if (checkFlag){
			this.saveDatas(null);
		}

	}
	public abstract boolean checkDatas(List<String> dataList);

	public abstract void saveDatas(List<String> dataList);
	
}
