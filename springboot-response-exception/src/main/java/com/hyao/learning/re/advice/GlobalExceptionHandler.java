package com.hyao.learning.re.advice;

import com.hyao.learning.re.exception.AppErrorCode;
import com.hyao.learning.re.exception.AppException;
import com.hyao.learning.re.model.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.*;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;
import java.util.StringJoiner;

/**
 * 全局异常处理。
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseData handleAppException(AppException ae) {
        LOGGER.error("应用框架异常: {}", ae.getMessage(), ae);
        return ResponseData.failure(ae.getCode(), ae.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseData handleException(Exception e) {
        LOGGER.error("系统内部异常：{}", e.getMessage(), e);
        return ResponseData.failure(AppErrorCode.UNKNOWN.getCode(), AppErrorCode.UNKNOWN.getMessage());
    }

    @Override
    protected @Nullable ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ServletWebRequest servletWebRequest = (ServletWebRequest) request;
        String requestURI = servletWebRequest.getRequest().getRequestURI();
        LOGGER.error("[请求异常]不支持的请求方法: {} {}", ex.getMethod(), requestURI, ex);
        ResponseData data = ResponseData.failure(AppErrorCode.UNSUPPORTED_REQUEST_METHOD, ex.getMethod());
        return createResponseEntity(data, headers, status, request);
    }

    @Override
    protected @Nullable ResponseEntity<Object> handleNoResourceFoundException(NoResourceFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ServletWebRequest servletWebRequest = (ServletWebRequest) request;
        String requestURI = servletWebRequest.getRequest().getRequestURI();
        LOGGER.error("[请求异常]资源未找到: {} {}", requestURI, ex.getMessage(), ex);
        ResponseData data = ResponseData.failure(AppErrorCode.NO_RESOURCE_FOUND, ex.getResourcePath());
        return createResponseEntity(data, headers, status, request);
    }

    @Override
    protected @Nullable ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ServletWebRequest servletWebRequest = (ServletWebRequest) request;
        String requestURI = servletWebRequest.getRequest().getRequestURI();
        List<ObjectError> allErrors = ex.getAllErrors();
        StringJoiner joiner = new StringJoiner("; ");
        allErrors.forEach(error -> joiner.add(error.getDefaultMessage()));
        LOGGER.error("[请求异常]请求参数校验不合法: {} {}", requestURI, joiner, ex);
        ResponseData data = ResponseData.failure(AppErrorCode.ILLEGAL_ARGUMENT, joiner.toString());
        return createResponseEntity(data, headers, status, request);
    }

    @Override
    protected @Nullable ResponseEntity<Object> handleHandlerMethodValidationException(HandlerMethodValidationException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ServletWebRequest servletWebRequest = (ServletWebRequest) request;
        String requestURI = servletWebRequest.getRequest().getRequestURI();
        List<? extends MessageSourceResolvable> allErrors = ex.getAllErrors();
        StringJoiner joiner = new StringJoiner("; ");
        allErrors.forEach(error -> joiner.add(error.getDefaultMessage()));
        LOGGER.error("[请求异常]请求参数校验不合法: {} {}", requestURI, joiner, ex);
        ResponseData data = ResponseData.failure(AppErrorCode.ILLEGAL_ARGUMENT, joiner.toString());
        return createResponseEntity(data, headers, status, request);
    }
}
