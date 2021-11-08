package com.zjm.dingtalk;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.PrintWriter;
import java.io.StringWriter;

@Slf4j
@RestControllerAdvice
public class GlobalException {

    @Autowired
    private ExceptionWarn exceptionWarn;


    /**
     * 运行时异常全局捕获
     *
     * @param e 抛出的异常
     * @return 封装结果类
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public void handle(Exception e) {
        log.error("GlobalException handle() failed,error message {}", e.getMessage(), e);
        doWarn(e);
    }

    /**
     * 告警
     *
     * @param e
     */
    private void doWarn(Exception e) {
        try {
            exceptionWarn.execute(WarnContent.builder().title(e.getMessage()).text(getStackTraceAsString(e)).build());
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }
    public static String getStackTraceAsString(Throwable ex) {
        StringWriter stringWriter = new StringWriter();
        ex.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

}