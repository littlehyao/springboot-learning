package com.hyao.learning.config.controller;

import com.hyao.learning.config.config.MyAppProperties;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {

    @Resource
    private MyAppProperties myAppProperties;

    @Value("${time-zone:Asia/Shanghai}")
    private String timeZone;

    @GetMapping("/test")
    public Map<String, Object> test() {
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
