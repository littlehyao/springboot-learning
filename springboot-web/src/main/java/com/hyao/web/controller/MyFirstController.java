package com.hyao.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class MyFirstController {

    /**
     * 页面渲染。
     */
    @RequestMapping(value = {"/hello", "/index"})
    public String hello(Model model) {
        model.addAttribute("title", "Hello, World!");
        model.addAttribute("message", "Welcome to Spring Boot!");
        return "index";
    }

    /**
     * JSON 接口请求。
     */
    @RequestMapping("/api/hello")
    @ResponseBody
    public Map<String, Object> hello() {
        return Map.of(
                "title", "Hello, World!",
                "message", "Welcome to Spring Boot!"
        );
    }
}
