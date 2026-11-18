package com.hyao.learning.re.exception;

/**
 * 应用异常码。
 */
public enum AppErrorCode implements IErrorCode{
    UNKNOWN("00000", "系统内部异常"),
    UNSUPPORTED_REQUEST_METHOD("10001", "不支持的请求方法: {}"),
    MISSING_PATH_VARIABLE("10002", "路径参数缺失: {}"),
    MISSING_SERVLET_REQUEST_PARAM("10003", "路径请求参数缺失: {}"),
    NO_RESOURCE_FOUND("10004", "请求资源未找到: {}"),
    HTTP_MESSAGE_NOT_READABLE("10005", "请求体读取错误: {}"),
    ILLEGAL_ARGUMENT("10006", "请求参数校验失败: {}"),
    ;

    private final String code;
    private final String message;

    AppErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

