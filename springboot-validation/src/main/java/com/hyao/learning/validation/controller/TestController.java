package com.hyao.learning.validation.controller;

import com.hyao.learning.validation.model.SampleRequestDTO;
import com.hyao.learning.validation.model.UserRegister;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class TestController {
    private static final Map<String, Object> RESULT =
            Map.of("result", "success");
    @PostMapping("/test")
    public Map<String, Object> test(@Validated @RequestBody SampleRequestDTO req) {
        return RESULT;
    }

    @PostMapping("/test/userRegister")
    public Map<String, Object> userRegister(@Validated @RequestBody UserRegister userRegister) {
        return RESULT;
    }
}
