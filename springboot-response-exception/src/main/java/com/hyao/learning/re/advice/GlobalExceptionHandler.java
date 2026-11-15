package com.hyao.learning.re.advice;

import com.hyao.learning.re.exception.AppErrorCode;
import com.hyao.learning.re.exception.AppException;
import com.hyao.learning.re.model.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseData handleAppException(AppException ae) {
        LOGGER.error("应用框架异常: {}", ae.getMessage(), ae);
        return ResponseData.failure(ae.getCode(), ae.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseData handleException(Exception e) {
        LOGGER.error("系统内部异常：{}", e.getMessage(), e);
        return ResponseData.failure(AppErrorCode.UNKNOWN);
    }
}
