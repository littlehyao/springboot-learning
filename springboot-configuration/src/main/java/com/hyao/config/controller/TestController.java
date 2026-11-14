package com.hyao.config.controller;

import com.hyao.config.config.MyAppProperties;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class TestController {

    @Resource
    private MyAppProperties myAppProperties;

    @Value("${time-zone:Asia/Shanghai}")
    private String timeZone;

    @GetMapping("/test")
    public Map<String, Object> test() {
        log.debug("[Spring 配置绑定] 调试...");
        Map<String, Object> map = new HashMap<>();
        map.put("timeZone", timeZone);
        map.put("name", myAppProperties.getName());
        map.put("env", myAppProperties.getEnv());
        map.put("lastDeploy", myAppProperties.getLastDeploy());
        Map<String, Object> tagMap = new HashMap<>();
        tagMap.put("id", myAppProperties.getTag().getId());
        tagMap.put("name", myAppProperties.getTag().getName());
        tagMap.put("author", myAppProperties.getTag().getAuthor());
        map.put("tag", tagMap);
        return map;
    }
}
