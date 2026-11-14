package com.hyao.fli.filter;

import jakarta.servlet.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class SampleFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("[SampleFilter] 初始化时调用");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("[SampleFilter] 过滤方法, 前置逻辑");
        chain.doFilter(request, response);
        log.info("[SampleFilter] 过滤方法, 后置逻辑");
    }

    @Override
    public void destroy() {
        log.info("[SampleFilter] 销毁方法");
    }
}
