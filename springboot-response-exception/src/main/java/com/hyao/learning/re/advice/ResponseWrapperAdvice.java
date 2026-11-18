package com.hyao.learning.re.advice;

import com.hyao.learning.re.model.ResponseData;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.JacksonJsonHttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

/**
 * 响应体包装类。
 */
@ControllerAdvice
public class ResponseWrapperAdvice implements ResponseBodyAdvice<Object> {
    private final ObjectMapper objectMapper;

    public ResponseWrapperAdvice(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        Class<?> parameterType = returnType.getParameterType();
        if (ResponseData.class.isAssignableFrom(parameterType)) {
            return false;
        }
        if (ResponseEntity.class.isAssignableFrom(parameterType)) {
            return false;
        }
        if (CharSequence.class.isAssignableFrom(parameterType)) {
            return true;
        }
        return JacksonJsonHttpMessageConverter.class.isAssignableFrom(converterType);
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {
        ResponseData wrapped = ResponseData.success(body);
        if (CharSequence.class.isAssignableFrom(returnType.getParameterType())) {
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            try {
                return objectMapper.writeValueAsString(wrapped);
            } catch (JacksonException e) {
                throw new RuntimeException("响应体序列化包装失败", e);
            }
        }
        return wrapped;
    }
}
