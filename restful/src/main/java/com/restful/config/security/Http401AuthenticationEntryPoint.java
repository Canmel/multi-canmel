package com.restful.config.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 * 设置entrypoint
 * 当产生401HttpCode时的操作
 * 如果是ajax请求返回msg，不是则返回登录页面
 * </p>
 *
 * @author  *
 *   ┏ ┓   ┏ ┓
 *  ┏┛ ┻━━━┛ ┻┓
 *  ┃         ┃
 *  ┃    ━    ┃
 *  ┃  ┳┛  ┗┳ ┃
 *  ┃         ┃
 *  ┃    ┻    ┃
 *  ┃         ┃
 *  ┗━━┓    ┏━┛
 *     ┃    ┃神兽保佑
 *     ┃    ┃代码无BUG！
 *     ┃    ┗━━━━━━━┓
 *     ┃            ┣┓
 *     ┃            ┏┛
 *     ┗┓┓┏━━━━━━┳┓┏┛
 *      ┃┫┫      ┃┫┫
 *      ┗┻┛      ┗┻┛
 * @since 2018-08-12
 */
public class Http401AuthenticationEntryPoint implements AuthenticationEntryPoint {
    private String msg;

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        if (isAjaxRequest(httpServletRequest)) {
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, StringUtils.isEmpty(msg) ? e.getMessage() : msg);
        } else {
            httpServletResponse.sendRedirect("/error/401");
        }
    }

    public Http401AuthenticationEntryPoint(String msg) {
        this.msg = msg;
    }

    public static boolean isAjaxRequest(HttpServletRequest request) {
        String ajaxFlag = request.getHeader("X-Requested-With");
        return ajaxFlag != null && "XMLHttpRequest".equals(ajaxFlag);
    }

}
