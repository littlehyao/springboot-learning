package com.hyao.fli.config;

import com.hyao.fli.filter.SampleAnnotatedFilter;
import com.hyao.fli.filter.SampleFilter;
import org.springframework.boot.web.servlet.FilterRegistration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<SampleFilter> sampleFilterRegistration() {
        FilterRegistrationBean<SampleFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new SampleFilter());
        registrationBean.addUrlPatterns("/**");
        registrationBean.setOrder(1);
        registrationBean.setAsyncSupported(true);
        registrationBean.setName("SampleFilter");
        return registrationBean;
    }

    @Bean
    @FilterRegistration(name = "SampleAnnotatedFilter", urlPatterns = {"/**"}, order = 1, asyncSupported = false)
    public SampleAnnotatedFilter sampleAnnotatedFilter() {
        return new SampleAnnotatedFilter();
    }
}
