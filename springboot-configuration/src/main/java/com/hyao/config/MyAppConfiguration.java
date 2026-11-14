package com.hyao.config;

import com.hyao.config.config.MyAppProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(MyAppProperties.class)
public class MyAppConfiguration {
}
