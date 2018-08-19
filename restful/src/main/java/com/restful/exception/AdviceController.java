package com.restful.exception;

import com.core.entity.ErrorResult;
import com.core.entity.HttpResult;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;

/**
 *
 *  前端控制器
 *
 * @author baily
 * 描述:
 * ${DESCRIPTION}
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
 * @since 2018年08月17日
 */
@RestControllerAdvice
@Component
public class AdviceController {
    @ExceptionHandler(value = UnAuthenticationException.class)
    public HttpResult unAhenticationException(){
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        return ErrorResult.UNAUTHORIZED();
    }

    @ExceptionHandler(value = WorkFlowInstanceAlreadyExistException.class)
    public HttpResult workFlowInstanceAlreadyExistException(WorkFlowInstanceAlreadyExistException e){
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return ErrorResult.BAD_REQUEST(e.getLocalizedMessage());
    }
}
