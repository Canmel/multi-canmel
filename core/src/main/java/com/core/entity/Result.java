package com.core.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class Result extends HttpResult {

    @Autowired
    HttpSession httpSession;

    public static final String NOT_FOUND_MSG = "未找到请求页面";

    public static final String SUCCESS_DEFAULT_MSG = "请求成功";

    public String access_token;

    public Object data;

    public Result(Integer httpStatus, String access_token) {
        this.httpStatus = httpStatus;
        this.access_token = access_token;
    }

    public Result(Integer httpStatus, Object data) {
        this.httpStatus = httpStatus;
        this.data = data;
    }

    public Result(Integer httpStatus, String msg, String access_token) {
        this.httpStatus = httpStatus;
        this.msg = msg;
        this.access_token = access_token;
    }

    public Result(Integer httpStatus, String msg, String access_token, Object data) {
        this.httpStatus = httpStatus;
        this.msg = msg;
        this.access_token = access_token;
        this.data = data;
    }

    public Result() {
    }

    public static Result OK(Object data) {
        String access_token = "";
        Object oAccessToken = getRequest().getSession().getAttribute("ACCESS_TOKEN");
        if (!ObjectUtils.isEmpty(oAccessToken)) {
            access_token = oAccessToken.toString();
        }
        return new Result(HttpStatus.OK.value(), SUCCESS_DEFAULT_MSG, access_token, data);
    }

    public static Result OK(String msg) {
        HttpServletRequest request = getRequest();
        String access_token = "";
        Object oAccessToken = request.getSession().getAttribute("ACCESS_TOKEN");
        if (!ObjectUtils.isEmpty(oAccessToken)) {
            access_token = oAccessToken.toString();
        }
        return new Result(HttpStatus.OK.value(), msg, access_token);
    }

    public static Result OK(String msg, Object obj) {
        String access_token = "";
        Object oAccessToken = getRequest().getSession().getAttribute("ACCESS_TOKEN");
        if (!ObjectUtils.isEmpty(oAccessToken)) {
            access_token = oAccessToken.toString();
        }
        return new Result(HttpStatus.OK.value(), msg, access_token, obj);
    }
}
