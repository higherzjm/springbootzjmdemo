package com.zjm.base.Interceptors;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.zjm.base.RequestVO;
import com.zjm.base.service.MyService;
import com.zjm.base.StudentInfo;
import com.zjm.base.util.JsonUtil;
import com.zjm.base.wrapper.CustomHttpRequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.*;

@Slf4j
public class MyInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private MyService myService;
    private CompletionService<String> completionService;

    public MyInterceptor() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(8, 30, 60, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(100), new ThreadFactoryBuilder().setNameFormat("salary-calculation-pool-%d").build(),
                new ThreadPoolExecutor.AbortPolicy());
        threadPoolExecutor.allowCoreThreadTimeOut(true);//允许核心线程超时
        completionService = new ExecutorCompletionService<>(threadPoolExecutor);
    }

    /**
     * 在请求处理之前进行调用（Controller方法调用之前）
     * 基于URL实现的拦截器
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String path = request.getServletPath();
        System.out.println("path:" + path);
        if (true) {
            if (path.contains("myboot")) {
                int pathSize = path.length();
                //无参数解析
                if (path.contains("test5")) {
                    RecheckThreadTaskCallable recheckThreadTaskCallable = new RecheckThreadTaskCallable("无参数解析返回结果");
                    String subRet = completionService.submit(recheckThreadTaskCallable).get();
                    log.info(subRet);

                    return true;
                }
                //get 请求，简单一个或多参数解析
                if (path.contains("test2")) {
                    String paramString = path.substring("/myboot/test2/".length(), pathSize);
                    Object[] paramArray = paramString.split("/");
                    String name = paramArray[0].toString();
                    Integer age = Integer.parseInt(paramArray[1].toString());

                    StudentInfo studentInfo = myService.getStudentInfo2(name, age);
                    log.info(studentInfo.toString());
                    RecheckThreadTaskCallable recheckThreadTaskCallable = new RecheckThreadTaskCallable("简单一个或多参数解析返回结果");
                    String subRet = completionService.submit(recheckThreadTaskCallable).get();
                    log.info(subRet);
                    return true;
                }
                //post请求，实体请求参数解析
                if (path.contains("test1")) {
                    //InputStream inputStream = request.getInputStream();
                    //BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuffer stringBuilder = new StringBuffer();
                    /*  char[] charBuffer = new char[1024];
                        int bytesRead;
                        while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                            stringBuilder.append(charBuffer, 0, bytesRead);
                        }
                    */

                   /* String tempInfo;
                    while ((tempInfo = bufferedReader.readLine()) != null) {
                        stringBuilder.append(tempInfo);
                    }
                    */

                    CustomHttpRequestWrapper requestWrapper = new CustomHttpRequestWrapper(request);
                    BufferedReader wrapperReader = requestWrapper.getReader();
                    String str;
                    while ((str = wrapperReader.readLine()) != null) {
                        stringBuilder.append(str);
                    }
                    log.info("请求stringBuilder:" + stringBuilder.toString());
                    RequestVO requestVO = JsonUtil.convertJsonToBean(stringBuilder.toString(), RequestVO.class);
                    log.info("请求VO:" + requestVO);

                    RecheckThreadTaskCallable recheckThreadTaskCallable = new RecheckThreadTaskCallable("实体请求参数解析返回结果");
                    String subRet = completionService.submit(recheckThreadTaskCallable).get();
                    log.info(subRet);
                    return true;
                }
                //请求转发
                if (path.contains("test4")) {
                    String name = path.substring(path.lastIndexOf("/") + 1, pathSize);
                    RecheckThreadTaskCallable recheckThreadTaskCallable = new RecheckThreadTaskCallable("请求转发返回结果");
                    String subRet = completionService.submit(recheckThreadTaskCallable).get();
                    log.info(subRet);
                    request.setAttribute("msg", "请求转发:" + name + "--" + subRet);
                    request.getRequestDispatcher("/myboot/requestForward").forward(request, response);
                    return false;
                }
            }
            return true;
        } else {
            // 这写你拦截需要干的事儿，比如取缓存，SESSION，权限判断等  
            System.out.println("====================================");
            return true;
        }
    }
}

