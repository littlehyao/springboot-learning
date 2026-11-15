package com.hyao.learning.re.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.hyao.learning.re.exception.IErrorCode;
import lombok.Data;

@Data
@JsonPropertyOrder({"code", "message", "data"})
public class ResponseData {
    private String code;
    private String message;
    private Object data;

    public ResponseData(String code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static ResponseData failure(IErrorCode errorCode) {
        return failure(errorCode.getCode(), errorCode.getMessage());
    }

    public static ResponseData failure(String code, String message) {
        return new ResponseData(code, message, null);
    }

    public static ResponseData success() {
        return success(null);
    }

    public static ResponseData success(Object data) {
        return new ResponseData("success", "", data);
    }
}
