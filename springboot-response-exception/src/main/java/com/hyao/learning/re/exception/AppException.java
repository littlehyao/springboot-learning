package com.hyao.learning.re.exception;

import lombok.Getter;
import org.slf4j.helpers.MessageFormatter;

/**
 * 应用系统异常。
 */
@Getter
public class AppException extends RuntimeException {
    private final String code;
    private final String message;

    public AppException() {
        this.code = AppErrorCode.UNKNOWN.getCode();
        this.message = AppErrorCode.UNKNOWN.getMessage();
    }

    public AppException(AppErrorCode appErrorCode, String... args) {
        this.code = appErrorCode.getCode();
        this.message = MessageFormatter.format(appErrorCode.getMessage(), args).getMessage();
    }
}
