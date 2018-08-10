package com.core.entity;

import org.springframework.http.HttpStatus;

public class Result extends HttpResult {

    public static final String NOT_FOUND_MSG = "未找到请求页面";

    public Integer httpStatus;
    public String msg;
    public String access_token;

    public Result(Integer httpStatus, String access_token) {
        this.httpStatus = httpStatus;
        this.access_token = access_token;
    }

    public Result(Integer httpStatus, String msg, String access_token) {
        this.httpStatus = httpStatus;
        this.msg = msg;
        this.access_token = access_token;
    }

    public Result() {
    }

    public static Result OK(String access_token){
        return new Result(HttpStatus.OK.value(), access_token);
    }

    public static Result OK(String access_token, String msg){
        return new Result(HttpStatus.OK.value(), msg, access_token);
    }

}
