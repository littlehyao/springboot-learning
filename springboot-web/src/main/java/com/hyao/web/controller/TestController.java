package com.hyao.web.controller;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class TestController {

    /**
     * {@code @RequestParam} 示例.
     * <pre>
     *     请求示例
     *     GET /test/get1?id=123456
     * </pre>
     */
    @GetMapping("/get1")
    public Map<String, Object> get1(@RequestParam("id") String id) {
        return Map.of("id", id);
    }

    /**
     * {@code @PathVariable} 示例.
     * <pre>
     *     请求示例
     *     GET /test/get2/123456
     * </pre>
     */
    @GetMapping("/get2/{id}")
    public Map<String, Object> get2(@PathVariable("id") String id) {
        return Map.of("id", id);
    }

    /**
     * {@code @RequestBody} 示例。
     * <pre>
     *     请求示例
     *     POST /post
     *
     *     ContentType: application/json
     *     { "name": "hyao" }
     * </pre>
     */
    @PostMapping("/post")
    public Map<String, Object> post(@RequestBody Map<String, Object> params) {
        return Map.copyOf(params);
    }
}
