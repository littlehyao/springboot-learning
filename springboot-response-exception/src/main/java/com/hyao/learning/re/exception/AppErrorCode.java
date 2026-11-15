package com.hyao.learning.re.exception;

public enum AppErrorCode implements IErrorCode{
    UNKNOWN("system.unknown", "系统内部异常"),
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

