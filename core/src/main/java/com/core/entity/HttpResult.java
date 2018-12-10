package com.core.entity;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class HttpResult {
    public Integer httpStatus;
    public String msg;

    public static HttpServletRequest getRequest(){
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return servletRequestAttributes.getRequest();
    }

    public static HttpServletResponse getResponse(){
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        return response;
    }
}
