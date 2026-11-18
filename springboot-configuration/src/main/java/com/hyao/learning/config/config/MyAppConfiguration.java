package com.hyao.learning.config.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(MyAppProperties.class)
public class MyAppConfiguration {
}
