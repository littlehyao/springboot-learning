package com.hyao.fli.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebListener
public class SampleListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("[SampleListener] servlet 初始化时调用");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("[SampleListener] servlet 销毁时调用");
    }
}
