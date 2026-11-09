package com.hyao.fli.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SampleContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("[SampleContextListener] 应用上下文初始化时调用");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("[SampleContextListener] 应用上下文销毁时调用");
    }
}
