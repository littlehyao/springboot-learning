package com.hyao.learning.re.controller;

import com.hyao.learning.re.exception.AppErrorCode;
import com.hyao.learning.re.exception.AppException;
import com.hyao.learning.re.model.ResponseData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/responseWrapper")
    public ResponseData responseWrapper(@RequestParam("id") String id) {
        return ResponseData.success(id);
    }

    @GetMapping("/testException")
    public ResponseData testException() {
        throw new AppException(AppErrorCode.UNKNOWN);
    }
}
