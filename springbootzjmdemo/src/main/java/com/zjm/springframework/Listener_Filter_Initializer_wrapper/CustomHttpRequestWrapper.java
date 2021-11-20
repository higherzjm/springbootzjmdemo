package com.zjm.springframework.Listener_Filter_Initializer_wrapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StreamUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author 朱建明
 * @Description: 流数据只能读取一次, 如果在拦截器中需要对post参数进行处理的话，就会报Required request body is missing 异常。需要将流保存起来就可以解决问题
 * @date 2021/3/28 11:47
 * @return
 */
@Slf4j
public class CustomHttpRequestWrapper extends HttpServletRequestWrapper {

    private byte[] requestBody = null;//用于将流保存下来
    //自定义包装器
    public CustomHttpRequestWrapper(HttpServletRequest request) {
        super(request);
        try {
            requestBody = StreamUtils.copyToByteArray(request.getInputStream());
        } catch (IOException e) {
            log.error("Wrap requestBody failed");
        }
    }

    @Override
    public ServletInputStream getInputStream() {
        if (requestBody == null) {
            requestBody = new byte[0];
        }
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(requestBody);
        return new ServletInputStream() {

            @Override
            public int read() {
                return byteArrayInputStream.read();
            }

            @Override
            public void setReadListener(ReadListener listener) {
                // do nothing
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public boolean isFinished() {
                return false;
            }
        };
    }

    @Override//对外提供读取流的方法
    public BufferedReader getReader() {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

}