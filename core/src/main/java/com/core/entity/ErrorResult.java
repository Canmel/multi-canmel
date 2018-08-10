package com.core.entity;

import org.springframework.http.HttpStatus;

public class ErrorResult extends HttpResult {

    public static final String NOT_FOUND_MSG = "未找到请求页面";

    public Integer httpStatus;
    public String msg;

    public ErrorResult(Integer httpStatus, String msg) {
        this.httpStatus = httpStatus;
        this.msg = msg;
    }

    public ErrorResult() {
    }

    public static ErrorResult UNAUTHORIZED(String msg) {
        return new ErrorResult(HttpStatus.UNAUTHORIZED.value(), msg);
    }

    public static ErrorResult NOTFOUND() {
        return new ErrorResult(HttpStatus.NOT_FOUND.value(), NOT_FOUND_MSG);
    }
}
