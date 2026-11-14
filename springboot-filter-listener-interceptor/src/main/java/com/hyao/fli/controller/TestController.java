package com.hyao.fli.controller;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class TestController {

    @GetMapping("/test")
    public Map<String, Object> test(@RequestParam("id") String id) {
        return Map.of("id", id);
    }

}
