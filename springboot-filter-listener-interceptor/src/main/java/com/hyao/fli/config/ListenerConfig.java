package com.hyao.fli.config;

import com.hyao.fli.listener.SampleContextListener;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ListenerConfig {

    @Bean
    public ServletListenerRegistrationBean<SampleContextListener> sampleContextListenerRegistration() {
        ServletListenerRegistrationBean<SampleContextListener> registrationBean = new ServletListenerRegistrationBean<>();
        registrationBean.setListener(new SampleContextListener());
        registrationBean.setOrder(1);
        return registrationBean;
    }

}
