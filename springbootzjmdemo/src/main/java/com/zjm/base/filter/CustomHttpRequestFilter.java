package com.zjm.base.filter;


import com.zjm.base.wrapper.CustomHttpRequestWrapper;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(urlPatterns = "/base/treeOperation/*", filterName = "customFilter")
public class CustomHttpRequestFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig){
        System.out.println("������2��ʼ��ʼ��");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("������2��ʼ����");
        ServletRequest requestWrapper=null;
        if(request instanceof HttpServletRequest) {
            requestWrapper=new CustomHttpRequestWrapper((HttpServletRequest)request);
        }
        if(requestWrapper==null) {
            chain.doFilter(request, response);
        }else {
            chain.doFilter(requestWrapper, response);
        }

    }

    @Override
    public void destroy() {
        System.out.println("������2����");
    }
}
