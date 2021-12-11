package com.zjm.dingtalkexceptioncatch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.UnexpectedTypeException;
import java.io.PrintWriter;
import java.io.StringWriter;

@Slf4j
@RestControllerAdvice
public class GlobalException {

    @Autowired
    private ExceptionWarn exceptionWarn;

    /**
     * 运行时异常全局捕获
     */
   @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public void handle(Exception e) {
        log.error("GlobalException handle() failed,error message {}", e.getMessage(), e);
        doWarn(e);
    }
    /**
     * 参数类型异常补充
     */
    @ExceptionHandler(UnexpectedTypeException.class)
    @ResponseStatus(HttpStatus.OK)
    public String handleError(UnexpectedTypeException e) {
        log.warn("参数验证失败", e.getMessage());
        doWarn(e);
       // return  handleError(e.getStackTrace());;
        return "参数验证失败";
    }
    private String handleError(BindingResult result) {
        FieldError error = result.getFieldError();
        String message = null;
        String fieldMessage = error.getDefaultMessage();
        message = String.format("%s:%s", error.getField(), fieldMessage);
        return message;
    }

    /**
     * 钉钉机器人同步
     */
    private void doWarn(Exception e) {
        try {
            //调用钉钉机器人传递异常信息
            exceptionWarn.execute(WarnContent.builder().title(e.getMessage()).text(getStackTraceAsString(e)).build());
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }
    /**
     * 转换栈追踪信息
     */
    private static String getStackTraceAsString(Throwable ex) {
        StringWriter stringWriter = new StringWriter();
        ex.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

}