package com.zjm.log;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhujianming
 */
@Slf4j
public class LogMain {
    private static final Logger LOGGER  = LoggerFactory.getLogger(LogMain.class);
    public static  void main(String[] args){
        log.info("Slf4j  ��־ݔ�� {}{}��","�����й���,","58");
        LOGGER.info("LoggerFactory  ��� {}{}��","���Ǹ�����,","20");
    }
}
