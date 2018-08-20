package com.core.entity;

import org.springframework.http.HttpStatus;

public class ErrorResult extends HttpResult {

    public static final String NOT_FOUND_MSG = "未找到请求页面";
    public static final String UNAUTHORIZED_MSG = "授权信息已过期";
    public static final String EXPECTATION_FAILED_MSG = "操作未完成，请检查参数";
    public static final String BAD_REQUEST_MSG = "请求参数不正确";


    public ErrorResult(Integer httpStatus, String msg) {
        this.httpStatus = httpStatus;
        this.msg = msg;
    }

    public ErrorResult() {
    }

    public static ErrorResult UNAUTHORIZED(String msg) {
        return new ErrorResult(HttpStatus.UNAUTHORIZED.value(), msg);
    }

    public static ErrorResult UNAUTHORIZED() {
        return new ErrorResult(HttpStatus.UNAUTHORIZED.value(), UNAUTHORIZED_MSG);
    }

    public static ErrorResult BAD_REQUEST(String msg) {
        return new ErrorResult(HttpStatus.BAD_REQUEST.value(), msg);
    }

    public static ErrorResult BAD_REQUEST() {
        return new ErrorResult(HttpStatus.BAD_REQUEST.value(), BAD_REQUEST_MSG);
    }

    public static ErrorResult NOTFOUND() {
        return new ErrorResult(HttpStatus.NOT_FOUND.value(), NOT_FOUND_MSG);
    }

    public static ErrorResult EXPECTATION_FAILED(String msg) {
        return new ErrorResult(HttpStatus.EXPECTATION_FAILED.value(), msg);
    }

    public static ErrorResult EXPECTATION_FAILED() {
        return new ErrorResult(HttpStatus.EXPECTATION_FAILED.value(), EXPECTATION_FAILED_MSG);
    }
}
