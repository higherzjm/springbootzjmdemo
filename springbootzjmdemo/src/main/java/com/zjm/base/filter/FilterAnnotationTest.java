package com.zjm.base.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(urlPatterns = "/redis/*", filterName = "filter2")
public class FilterAnnotationTest implements Filter {

    @Override
    public void init(FilterConfig filterConfig){
        System.out.println("过滤器2开始初始化");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("过滤器2开始工作");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        System.out.println("过滤器2销毁");
    }
}
